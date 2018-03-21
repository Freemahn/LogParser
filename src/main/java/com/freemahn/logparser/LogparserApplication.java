package com.freemahn.logparser;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class LogparserApplication {

    public static void main(String[] args) {
        System.out.print("Starting Spring app..");
        new SpringApplicationBuilder(LogparserApplication.class)
                .logStartupInfo(false)
                .run(args);
    }
}
