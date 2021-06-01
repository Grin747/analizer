package com.example.analizer;

import com.example.analizer.services.ModuleExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.Scanner;

@SpringBootApplication
public class AnalizerApplication {
	private static ModuleExecutorService executor;

	@Autowired
	public AnalizerApplication(ModuleExecutorService executor) {
		AnalizerApplication.executor = executor;
	}

	public static void main(String[] args) {
		SpringApplication.run(AnalizerApplication.class, args);

		while (true) {
			var file = new File(new Scanner(System.in).nextLine());
			executor.process(file);
		}
	}

}
