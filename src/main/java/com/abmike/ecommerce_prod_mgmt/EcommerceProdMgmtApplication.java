package com.abmike.ecommerce_prod_mgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class EcommerceProdMgmtApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceProdMgmtApplication.class, args);
    }

}