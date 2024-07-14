package com.abmike.ecommerce_prod_mgmt.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfig {

    @Bean
    public InfoContributor getInfoContributor() {
        return (builder) -> {
            builder.withDetail("app_name", "E-Commerce Product Management")
                    .withDetail("version", "1.0.0");
        };
    }
}