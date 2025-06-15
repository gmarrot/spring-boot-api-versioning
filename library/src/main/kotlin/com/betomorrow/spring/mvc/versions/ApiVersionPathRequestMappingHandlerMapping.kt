package com.betomorrow.spring.mvc.versions

import org.springframework.core.annotation.AnnotatedElementUtils
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
        val methodRequestMappingInfo = this.createRequestMappingInfo(method) ?: return null
        val typeRequestMappingInfo = this.createRequestMappingInfo(handlerType)
        return if (typeRequestMappingInfo != null) {
            typeRequestMappingInfo.combine(methodRequestMappingInfo)
        } else {
            methodRequestMappingInfo
        }
    }

    private fun createRequestMappingInfo(element: AnnotatedElement): RequestMappingInfo? {
        val originalMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping::class.java) ?: return null
        val requestApiVersion = AnnotatedElementUtils.findMergedAnnotation(element, RequestApiVersion::class.java)

        if (requestApiVersion == null) {
            return this.createRequestMappingInfo(originalMapping, null)
        }

        val allowedApiVersions = requestApiVersion.value.map { ApiVersion(it) }.toSet()
        val paths =
            originalMapping.path.flatMap { originalPath ->
                allowedApiVersions.map { version -> originalPath.replace(pathVariableName, "$pathVersionPrefix${version.value}") }
            }

        return RequestMappingInfo
            .paths(*resolveEmbeddedValuesInPatterns(paths.toTypedArray()))
            .methods(*originalMapping.method)
            .params(*originalMapping.params)
            .headers(*originalMapping.headers)
            .consumes(*originalMapping.consumes)
            .produces(*originalMapping.produces)
            .build()
    }
}
