package com.example.analizer.interfaces;

import java.io.File;

public interface Module {
    boolean isAcceptableFormat(String format);
    String[] getFunctions();
    String exec(int func, File file);
    static String exception = "No such function";
}
