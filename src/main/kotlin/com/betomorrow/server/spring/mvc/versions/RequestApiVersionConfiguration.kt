package com.betomorrow.server.spring.mvc.versions

import com.betomorrow.server.spring.openapi.RequestApiVersionOperationCustomizer
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanNameGenerator
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.annotation.AnnotationAttributes
import org.springframework.core.type.AnnotationMetadata

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@Import(RequestApiVersionBeanRegistrar::class, RequestApiVersionOpenApiBeanRegistrar::class)
@MustBeDocumented
annotation class EnableRequestApiVersion(
    val headerName: String = "x-api-version",
)

class RequestApiVersionBeanRegistrar : ImportBeanDefinitionRegistrar {
    override fun registerBeanDefinitions(
        metadata: AnnotationMetadata,
        registry: BeanDefinitionRegistry,
        beanNameGenerator: BeanNameGenerator,
    ) {
        val annotation = metadata.getAnnotationAttributes(EnableRequestApiVersion::class.java.name)
            ?: throw IllegalStateException("No annotation attributes found")

        val attributes = AnnotationAttributes(annotation)

        registerWebMvcRegistrations(attributes, registry, beanNameGenerator)
    }

    private fun registerWebMvcRegistrations(
        annotationAttributes: AnnotationAttributes,
        registry: BeanDefinitionRegistry,
        beanNameGenerator: BeanNameGenerator,
    ) {
        val builder = BeanDefinitionBuilder.rootBeanDefinition(ApiVersionWebMvcRegistrations::class.java)
        builder.addConstructorArgValue(annotationAttributes.getString(EnableRequestApiVersion::headerName.name))
        registry.registerIfNotExists(builder, beanNameGenerator)
    }
}

class RequestApiVersionOpenApiBeanRegistrar : ImportBeanDefinitionRegistrar {
    override fun registerBeanDefinitions(
        metadata: AnnotationMetadata,
        registry: BeanDefinitionRegistry,
        beanNameGenerator: BeanNameGenerator,
    ) {
        if (isClassPresent("io.swagger.v3.oas.models.OpenAPI")) {
            val annotation = metadata.getAnnotationAttributes(EnableRequestApiVersion::class.java.name)
                ?: throw IllegalStateException("No annotation attributes found")

            val attributes = AnnotationAttributes(annotation)

            registerOpenApiCustomizers(attributes, registry, beanNameGenerator)
        }
    }

    private fun registerOpenApiCustomizers(
        annotationAttributes: AnnotationAttributes,
        registry: BeanDefinitionRegistry,
        beanNameGenerator: BeanNameGenerator,
    ) {
        val builder = BeanDefinitionBuilder.rootBeanDefinition(RequestApiVersionOperationCustomizer::class.java)
        builder.addConstructorArgValue(annotationAttributes.getString(EnableRequestApiVersion::headerName.name))
        registry.registerIfNotExists(builder, beanNameGenerator)
    }
}

private fun BeanDefinitionRegistry.registerIfNotExists(builder: BeanDefinitionBuilder, beanNameGenerator: BeanNameGenerator) {
    builder.beanDefinition.let {
        val beanName = beanNameGenerator.generateBeanName(it, this)
        if (!this.containsBeanDefinition(beanName)) {
            this.registerBeanDefinition(beanName, it)
        }
    }
}

private fun isClassPresent(className: String): Boolean {
    return try {
        Class.forName(className)
        true
    } catch (_: ClassNotFoundException) {
        false
    }
}
