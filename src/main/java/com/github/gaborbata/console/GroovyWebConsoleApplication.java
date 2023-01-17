package com.github.gaborbata.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@SpringBootApplication
@ConditionalOnProperty(name = "groovy-web-console.application-enabled", havingValue = "true")
public class GroovyWebConsoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroovyWebConsoleApplication.class, args);
    }
}
