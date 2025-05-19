package com.br.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.br.tasks")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
