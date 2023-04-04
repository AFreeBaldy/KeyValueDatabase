package com.laudwilliam.keyvaluedatabase.utils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileManager {


    public boolean isFile(String path)
    {
        File file = new File(path);
        return file.exists();
    }

    public File getFile(String path) {
        return new File(path);
    }
}
