package com.betomorrow.server.spring.mvc.versions

import com.betomorrow.server.spring.openapi.RequestApiVersionOperationCustomizer
import io.swagger.v3.oas.models.OpenAPI
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@Import(RequestApiVersionConfiguration::class)
@MustBeDocumented
annotation class EnableRequestApiVersion

class RequestApiVersionConfiguration {
    @Bean
    fun requestApiVersionWebMvcRegistrations() = ApiVersionWebMvcRegistrations()

    @Bean
    @ConditionalOnBean(OpenAPI::class)
    fun requestApiVersionOperationCustomizer() = RequestApiVersionOperationCustomizer()
}
