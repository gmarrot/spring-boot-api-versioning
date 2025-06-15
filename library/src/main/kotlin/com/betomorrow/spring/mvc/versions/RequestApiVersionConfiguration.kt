package com.betomorrow.spring.mvc.versions

import com.betomorrow.spring.openapi.ApiVersionHeaderOperationCustomizer
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanNameGenerator
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.annotation.AnnotationAttributes
import org.springframework.core.type.AnnotationMetadata
import kotlin.reflect.KClass

enum class RequestApiVersionMode {
    HEADER,
    PATH,
}

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@Import(RequestApiVersionBeanRegistrar::class, RequestApiVersionOpenApiBeanRegistrar::class)
@MustBeDocumented
annotation class EnableRequestApiVersion(
    val mode: RequestApiVersionMode = RequestApiVersionMode.HEADER,
    val headerName: String = "x-api-version",
    val pathVariableName: String = "{api-version}",
    val pathVersionPrefix: String = "",
)

class RequestApiVersionBeanRegistrar : ImportBeanDefinitionRegistrar {
    override fun registerBeanDefinitions(
        metadata: AnnotationMetadata,
        registry: BeanDefinitionRegistry,
        beanNameGenerator: BeanNameGenerator,
    ) {
        val annotationAttributes = buildAnnotationAttributes(metadata, EnableRequestApiVersion::class)
        registerWebMvcRegistrations(annotationAttributes, registry, beanNameGenerator)
    }

    private fun registerWebMvcRegistrations(
        annotationAttributes: AnnotationAttributes,
        registry: BeanDefinitionRegistry,
        beanNameGenerator: BeanNameGenerator,
    ) {
        val mode = annotationAttributes.getEnum<RequestApiVersionMode>(EnableRequestApiVersion::mode.name)
        when (mode) {
            RequestApiVersionMode.HEADER -> {
                val builder = BeanDefinitionBuilder.rootBeanDefinition(ApiVersionHeaderWebMvcRegistrations::class.java)
                builder.addConstructorArgValue(annotationAttributes.getString(EnableRequestApiVersion::headerName.name))
                registry.registerIfNotExists(builder, beanNameGenerator)
            }

            RequestApiVersionMode.PATH -> {
                val builder = BeanDefinitionBuilder.rootBeanDefinition(ApiVersionPathWebMvcRegistrations::class.java)
                builder.addConstructorArgValue(annotationAttributes.getString(EnableRequestApiVersion::pathVariableName.name))
                builder.addConstructorArgValue(annotationAttributes.getString(EnableRequestApiVersion::pathVersionPrefix.name))
                registry.registerIfNotExists(builder, beanNameGenerator)
            }
        }
    }
}

class RequestApiVersionOpenApiBeanRegistrar : ImportBeanDefinitionRegistrar {
    override fun registerBeanDefinitions(
        metadata: AnnotationMetadata,
        registry: BeanDefinitionRegistry,
        beanNameGenerator: BeanNameGenerator,
    ) {
        if (!isClassPresent("io.swagger.v3.oas.models.OpenAPI")) return

        val annotationAttributes = buildAnnotationAttributes(metadata, EnableRequestApiVersion::class)
        registerOpenApiOperationCustomizers(annotationAttributes, registry, beanNameGenerator)
    }

    private fun registerOpenApiOperationCustomizers(
        annotationAttributes: AnnotationAttributes,
        registry: BeanDefinitionRegistry,
        beanNameGenerator: BeanNameGenerator,
    ) {
        val mode = annotationAttributes.getEnum<RequestApiVersionMode>(EnableRequestApiVersion::mode.name)
        if (mode == RequestApiVersionMode.HEADER) {
            val builder = BeanDefinitionBuilder.rootBeanDefinition(ApiVersionHeaderOperationCustomizer::class.java)
            builder.addConstructorArgValue(annotationAttributes.getString(EnableRequestApiVersion::headerName.name))
            registry.registerIfNotExists(builder, beanNameGenerator)
        }
    }
}

private fun buildAnnotationAttributes(metadata: AnnotationMetadata, annotationClazz: KClass<*>): AnnotationAttributes {
    val annotation =
        metadata.getAnnotationAttributes(annotationClazz.java.name)
            ?: throw IllegalStateException("No annotation attributes found")
    return AnnotationAttributes(annotation)
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
