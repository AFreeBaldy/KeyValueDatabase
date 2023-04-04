package com.laudwilliam.keyvaluedatabase.client;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.File;

@Component
public class Client {


    public String delete(String key)
    {

        return null;
    }

    public String set(String key, String value)
    {
        return null;
    }

    public String get(String key)
    {
        return null;
    }


    public boolean login(String username, String password)
    {
        return true;
    }

    public String processCommands(File input)
    {


        return "SUCCESS";
    }

    public String processCommands(File input, File output)
    {


        return "SUCCESS";
    }



}
