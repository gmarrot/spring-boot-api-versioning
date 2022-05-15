package com.betomorrow.sandbox.infra.spring

import org.springframework.core.annotation.AnnotatedElementUtils
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.condition.RequestCondition
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Method

class ApiVersionRequestMappingHandlerMapping : RequestMappingHandlerMapping() {
    override fun getCustomTypeCondition(handlerType: Class<*>): RequestCondition<*>? {
        return this.createRequestCondition(handlerType)
    }

    override fun getCustomMethodCondition(method: Method): RequestCondition<*>? {
        return this.createRequestCondition(method)
    }

    override fun getMappingForMethod(method: Method, handlerType: Class<*>): RequestMappingInfo? {
        val methodRequestMappingInfo = this.createRequestMappingInfo(method) ?: return null
        val typeRequestMappingInfo = this.createRequestMappingInfo(handlerType)
        return if (typeRequestMappingInfo != null) {
            typeRequestMappingInfo.combine(methodRequestMappingInfo)
        } else {
            methodRequestMappingInfo
        }
    }

    private fun createRequestMappingInfo(element: AnnotatedElement): RequestMappingInfo? {
        val requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping::class.java) ?: return null
        val requestCondition = this.createRequestCondition(element)
        return this.createRequestMappingInfo(requestMapping, requestCondition)
    }

    private fun createRequestCondition(element: AnnotatedElement): RequestCondition<ApiVersionRequestCondition>? {
        val requestApiVersion = AnnotationUtils.findAnnotation(element, RequestApiVersion::class.java) ?: return null
        val allowedApiVersions = requestApiVersion.value.map { ApiVersion(it) }.toSet()
        return ApiVersionRequestCondition(allowedApiVersions)
    }
}
