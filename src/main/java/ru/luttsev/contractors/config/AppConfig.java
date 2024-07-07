package ru.luttsev.contractors.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Конфигурация приложения
 * @author Yuri Luttsev
 */
@Configuration
@EnableJpaRepositories(basePackages = "ru.luttsev.contractors.repository")
public class AppConfig {
}
