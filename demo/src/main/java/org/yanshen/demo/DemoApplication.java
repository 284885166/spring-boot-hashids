package org.yanshen.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yanshen.springframework.WebApplication;

@SpringBootApplication
public class DemoApplication extends WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

