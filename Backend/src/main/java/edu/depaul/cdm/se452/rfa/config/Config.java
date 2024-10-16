package edu.depaul.cdm.se452.rfa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Objects;

@PropertySource("classpath:/application.properties")
@ComponentScan
@Configuration
public class Config {

    @Autowired
    private Environment env;

    @Bean
    ApplicationProperties appProperties() {
        return ApplicationProperties.builder()
                .dataSourceUrl(env.getProperty("spring.datasource.url"))
                .dataSourceUsername(env.getProperty("spring.datasource.username"))
                .dataSourcePassword(env.getProperty("spring.datasource.password"))
                .hikariIdleTimeout(Integer.parseInt(Objects.requireNonNull(env.getProperty("spring.datasource.hikari.idle-timeout"))))
                .hikariMaxLifetime(Integer.parseInt(Objects.requireNonNull(env.getProperty("spring.datasource.hikari.max-lifetime"))))
                .hikariMinimumIdleConnections(Integer.parseInt(Objects.requireNonNull(env.getProperty("spring.datasource.hikari.minimum-idle"))))
                .hikariMaximumPoolSize(Integer.parseInt(Objects.requireNonNull(env.getProperty("spring.datasource.hikari.maximum-pool-size"))))
                .jwtSecret(env.getProperty("app.jwtSecret"))
                .jwtExpirationInMs(Integer.parseInt(Objects.requireNonNull(env.getProperty("app.jwtExpirationInMs"))))
                .build();
    }
}