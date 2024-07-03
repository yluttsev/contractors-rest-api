package ru.luttsev.contractors.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "ru.luttsev.contractors.repository")
public class AppConfig {
}
