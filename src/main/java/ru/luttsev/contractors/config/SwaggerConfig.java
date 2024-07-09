package ru.luttsev.contractors.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "API для работы с контрагентами",
                contact = @Contact(
                        name = "Yuri Luttsev",
                        email = "floomyz@vk.com"
                )
        )
)
public class SwaggerConfig {
}
