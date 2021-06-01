package com.example.analizer.modules;

import com.drew.imaging.ImageProcessingException;
import com.example.analizer.interfaces.Module;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class TxtModule implements Module {
    @Override
    public boolean isAcceptableFormat(String format) {
        return format.equals("txt");
    }

    @Override
    public String[] getFunctions() {
        return new String[] {
                "1 - Count lines in file",
                "2 - Count character occurrence frequency",
                "3 - Count file size"
        };
    }

    @Override
    public String exec(int func, File file) {
        if (func > 3 || func < 1) return exception;

        try {
            var result = TxtModule.class.getMethod("op" + func, File.class).invoke(this, file);
            return (String) result;
        } catch (Exception e) {
            return exception;
        }
    }

    public String op1(File file) throws IOException {
        var linesCount = 0;
        var scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            linesCount++;
        }
        return linesCount + " lines";
    }

    public String op2(File file) throws IOException {
        var scanner = new Scanner(file);
        var map = new HashMap<Character, Integer>();
        var result = new StringBuilder().append("char : count\n");

        while (scanner.hasNext()) {
            var chars = scanner.next().toLowerCase().toCharArray();
            for (char c : chars) {
                if (!map.containsKey(c))
                    map.put(c, 0);
                map.put(c, map.get(c) + 1);
            }
        }

        for (var e : map.entrySet())
            result.append(e.getKey()).append(" : ").append(e.getValue()).append("\n");

        return result.toString();
    }

    public String op3(File file) throws IOException {
        return file.length() / 1024 + " KB";
    }
}
