package com.example.analizer.services;

import com.example.analizer.interfaces.Module;
import com.example.analizer.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;
import java.util.Scanner;

@Service
public class ModuleExecutorService {
    private final Collection<Module> modules;

    @Autowired
    public ModuleExecutorService(Collection<Module> modules) {
        this.modules = modules;
    }

    public void process(File file){

        var format = FileUtils.getFormatName(file);
        Module module = null;

        for (var m : modules)
            if (m.isAcceptableFormat(format)){
                module = m;
                break;
            }

        if (module == null){
            System.out.println("No acceptable module for this type");
            return;
        }

        var options = module.getFunctions();
        for (var o : options)
            System.out.println(o);

        var func = new Scanner(System.in).nextInt();

        System.out.println(module.exec(func, file));
        System.out.println("+----------------------------------------+");
    }
}
