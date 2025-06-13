package com.betomorrow.sandbox.api.swagger

import com.betomorrow.server.spring.openapi.apiVersionsToMatch
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration {
    @Bean
    fun v1GroupApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("API v1")
            .pathsToMatch("/**")
            .apiVersionsToMatch("1.0")
            .build()
    }

    @Bean
    fun v2GroupApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("API v2")
            .pathsToMatch("/**")
            .apiVersionsToMatch("2.0")
            .build()
    }
}
