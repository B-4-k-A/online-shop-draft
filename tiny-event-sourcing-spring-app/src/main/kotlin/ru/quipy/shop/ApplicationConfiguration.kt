package ru.quipy.shop

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import lombok.Value

import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.List


@Configuration
class ApplicationConfiguration {

    @Bean
    fun customOpenApi(): OpenAPI? {
        return OpenAPI().info(
            Info().title("online shop API")
                .version("1.0.1")
                .description("Online Shop")
                .license(
                    License().name("Apache 2.0")
                        .url("http://springdoc.org")
                )
                .contact(
                    Contact().name("eivanovJr")
                )
        )
    }
}