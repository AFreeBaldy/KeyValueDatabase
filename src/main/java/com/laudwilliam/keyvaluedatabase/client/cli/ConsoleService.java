package com.laudwilliam.keyvaluedatabase.client.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Console;

@Service
public class ConsoleService {
    private final Console console;

    @Autowired
    public ConsoleService() {
        console =  System.console();
        if (console == null)
        {
            throw new RuntimeException();
        }
    }

    public String getPassword() {
        return String.copyValueOf(console.readPassword());
    }

}
