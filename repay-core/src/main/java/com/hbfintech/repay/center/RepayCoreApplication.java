package com.hbfintech.repay.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class RepayCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepayCoreApplication.class, args);
    }

}
