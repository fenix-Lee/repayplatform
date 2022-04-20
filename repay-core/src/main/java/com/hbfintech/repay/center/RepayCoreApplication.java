package com.hbfintech.repay.center;

import com.hbfintech.pigeon.client.core.annotation.EnablePigeonClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnablePigeonClients
public class RepayCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepayCoreApplication.class, args);
    }

}
