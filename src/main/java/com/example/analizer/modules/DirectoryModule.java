package com.example.analizer.modules;

import com.example.analizer.interfaces.Module;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DirectoryModule implements Module {
    @Override
    public boolean isAcceptableFormat(String format) {
        return format.equals("dir");
    }

    @Override
    public String[] getFunctions() {
        return new String[] {
                "1 - List all files in directory",
                "2 - Calculate total size of files in directory",
                "3 - Count files and child directories in directory"
        };
    }

    @Override
    public String exec(int func, File file) {
        if (func > 3 || func < 1) return exception;

        try {
            var result = DirectoryModule.class.getMethod("op" + func, File.class).invoke(this, file);
            return (String) result;
        } catch (Exception e) {
            return exception;
        }
    }

    public String op1(File file){
        var files = file.listFiles();
        if (files == null || files.length == 0) return "No files here";

        var result = new StringBuilder();
        for (var f:files) result.append(f.getName()).append("\n");

        return result.toString();
    }

    public String op2(File file){
        var files = file.listFiles();
        if (files == null || files.length == 0) return "No files here";
        var result = 0L;

        for (var f:files) result += f.length();

        return (result / 1024) + " KB";
    }

    public String op3(File file){
        var files = file.listFiles();
        if (files == null || files.length == 0) return "No files here";

        var fileCount = 0;
        var dirCount = 0;

        for (var f:files) {
            if (f.isDirectory()) dirCount++;
            else fileCount++;
        }

        return "Files: " + fileCount + "\nDirectories: " + dirCount;
    }
}
