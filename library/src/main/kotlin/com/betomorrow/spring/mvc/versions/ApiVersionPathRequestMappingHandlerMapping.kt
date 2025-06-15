package com.betomorrow.spring.mvc.versions

import org.springframework.core.annotation.AnnotatedElementUtils
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Method

class ApiVersionPathRequestMappingHandlerMapping(
    private val pathVariableName: String,
    private val pathVersionPrefix: String,
) : RequestMappingHandlerMapping() {
    override fun getMappingForMethod(method: Method, handlerType: Class<*>): RequestMappingInfo? {
        val allowedApiVersions =
            getAllowedApiVersions(method, handlerType)
                ?: return super.getMappingForMethod(method, handlerType)

        val methodRequestMappingInfo = this.createRequestMappingInfo(method, allowedApiVersions) ?: return null
        val typeRequestMappingInfo = this.createRequestMappingInfo(handlerType, allowedApiVersions)
        return if (typeRequestMappingInfo != null) {
            typeRequestMappingInfo.combine(methodRequestMappingInfo)
        } else {
            methodRequestMappingInfo
        }
    }

    private fun getAllowedApiVersions(method: Method, handlerType: Class<*>): Set<ApiVersion>? {
        val requestApiVersion =
            AnnotationUtils.findAnnotation(method, RequestApiVersion::class.java)
                ?: AnnotationUtils.findAnnotation(handlerType, RequestApiVersion::class.java)
                ?: return null
        return requestApiVersion.value.map { ApiVersion(it) }.toSet()
    }

    private fun createRequestMappingInfo(element: AnnotatedElement, apiVersions: Set<ApiVersion>): RequestMappingInfo? {
        val originalMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping::class.java) ?: return null

        val paths =
            originalMapping.path.flatMap { originalPath ->
                apiVersions.map { version -> originalPath.replace(pathVariableName, "$pathVersionPrefix${version.value}") }
            }

        return RequestMappingInfo
            .paths(*resolveEmbeddedValuesInPatterns(paths.toTypedArray()))
            .methods(*originalMapping.method)
            .params(*originalMapping.params)
            .headers(*originalMapping.headers)
            .consumes(*originalMapping.consumes)
            .produces(*originalMapping.produces)
            .mappingName(originalMapping.name)
            .build()
    }
}
