package com.alpha.alphafc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.alpha.*")
public class AlphaFcApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlphaFcApplication.class, args);
    }

}
