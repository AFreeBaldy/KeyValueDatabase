package com.laudwilliam.keyvaluedatabase.client.cli;

import com.laudwilliam.keyvaluedatabase.client.http.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;

import java.io.Console;

@ShellComponent
public class Commands {
    private boolean loggedIn;
    private final HttpClient httpClient;

    @Autowired
    public Commands(HttpClient httpClient) {
        this.httpClient = httpClient;
    }


    @ShellMethod("login")
    public String login(
            String databaseName,
            @ShellOption(value = {"-u", "--username"}) String username,
            @ShellOption(value = {"-p", "--password"}, defaultValue = "false") boolean password
    ) {
        if (password) {
            boolean isValid = false;
            String pass;
            do {
                Console console = System.console();
                if (console == null) {
                    System.err.println("No console.");
                    System.exit(1);
                }
                char[] passwordArray = console.readPassword("Enter your password: ");
                pass = String.copyValueOf(passwordArray);
                if (StringUtils.hasText(pass))
                    isValid = true;
                else {
                    System.out.println("Enter a valid password: ");
                }
            } while (!isValid);
            if (httpClient.login(username, pass))
            {
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
    public void query(
            String queryName,
            String key,
            @ShellOption(value = {}, defaultValue = "") String value
    ) {
        System.out.println(queryName + " " + key + " " + value);
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

        return String.format("Input file path: %s  \nOutput file path%s", inputFilePath, outputFilePath);
    }


}
