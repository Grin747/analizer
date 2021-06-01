package com.example.analizer.utils;

import java.io.File;

public class FileUtils {
    public static String getFormatName(File file){

        if (file.isDirectory()) return "dir";

        var name = file.getName();
        if (name.lastIndexOf(".") != -1)
            return name.substring(name.lastIndexOf(".") + 1);

        return "unknown";
    }
}
