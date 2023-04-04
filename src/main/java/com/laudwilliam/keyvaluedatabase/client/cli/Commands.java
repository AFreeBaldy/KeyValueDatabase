package com.laudwilliam.keyvaluedatabase.client.cli;

import com.laudwilliam.keyvaluedatabase.client.Client;
import com.laudwilliam.keyvaluedatabase.client.language.QueryCommands;
import com.laudwilliam.keyvaluedatabase.utils.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;

@ShellComponent
public class Commands {
    private final ConsoleService consoleService;
    private final Client client;
    private final FileManager fileManger;
    private boolean loggedIn;

    @Autowired
    public Commands(ConsoleService consoleService, Client client, FileManager fileManager) {
        this.fileManger = fileManager;
        this.consoleService = consoleService;
        this.client = client;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    @ShellMethod("login")
    public String login(
            String databaseName,
            @ShellOption(value = {"-u", "--username"}) String username,
            @ShellOption(value = {"-p", "--password"}, defaultValue = "false") boolean password
    ) {
        if (!StringUtils.hasText(databaseName) || !StringUtils.hasText(username))
            return "Please enter a valid command";
        if (password) {
            boolean isValid = false;
            String pass;
            do {
                pass = consoleService.getPassword();
                if (StringUtils.hasText(pass))
                    isValid = true;
                else {
                    System.out.println("Enter a valid password: ");
                }
            } while (!isValid);
            if (client.login(username, pass)) {
                loggedIn = true;
                return "Successfully logged in";
            }
        } else
            return "Password is required";


        return "Incorrect username or password";
    }

    @ShellMethodAvailability("login")
    public Availability loginCommandAvailabilityCheck() {
        return loggedIn ? Availability.unavailable("You are already logged in") : Availability.available();
    }

    @ShellMethod("query")
    public String query(
            String queryName,
            String key,
            @ShellOption(defaultValue = "") String value
    ) {
        if (!StringUtils.hasText(queryName) || !StringUtils.hasText(key))
            return "Please enter a valid command";
        QueryCommands queryCommands;
        try {
            queryCommands = QueryCommands.valueOf(queryName);
        } catch (IllegalArgumentException exception) {
            return String.format("The query '%s' does not exist, type --help to get list of commands", queryName);
        }
        switch (queryCommands) {
            case DEL -> {
                if (StringUtils.hasText(value))
                    return "Value option is only available for the [SET] command";
                return client.delete(key);
            }
            case SET -> {return client.set(key, value);}
            case GET -> {
                if (StringUtils.hasText(value))
                    return "Value option is only available for the [SET] command";return client.get(key);
            }
            default -> {
                return "SUCCESS";
            }
        }
    }

    @ShellMethodAvailability({"query", "run"})
    public Availability loggedInCommandsAvailabilityCheck() {
        if (!loggedIn)
            return Availability.unavailable("You are not logged in");
        return Availability.available();
    }

    @ShellMethod("run")
    public String run(
            String inputFilePath,
            @ShellOption(value = {"-o", "--output-file"}, defaultValue = "") String outputFilePath
    ) {
        if (!StringUtils.hasText(inputFilePath))
            return "Please enter a valid command";
        boolean isInput = fileManger.isFile(inputFilePath);
        if (!isInput)
            return String.format("File not found %s: ", inputFilePath);
        if (StringUtils.hasText(outputFilePath) && fileManger.isFile(outputFilePath))
            return "Output file already exist";
        if (StringUtils.hasText(outputFilePath))
            return client.processCommands(fileManger.getFile(inputFilePath), fileManger.getFile(outputFilePath));

        return client.processCommands(fileManger.getFile(inputFilePath));
    }


}
