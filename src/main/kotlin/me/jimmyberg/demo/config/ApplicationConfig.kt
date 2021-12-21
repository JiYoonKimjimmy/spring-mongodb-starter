package me.jimmyberg.demo.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "AMS Swagger",
        version = "1.0",
        description = "Academy Management Service"
    )
)
class ApplicationConfig