package com.journalintime.infrastructure.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration principale de l'application Spring Boot.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.journalintime")
@EntityScan(basePackages = "com.journalintime.domain.entity")
@EnableJpaAuditing
@EnableTransactionManagement
public class ApplicationConfig {
}
