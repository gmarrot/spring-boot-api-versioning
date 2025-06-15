package com.betomorrow.spring.openapi

import com.betomorrow.spring.findMergedAnnotation
import com.betomorrow.spring.mvc.versions.ApiVersion
import com.betomorrow.spring.mvc.versions.RequestApiVersion
import org.springdoc.core.filters.OpenApiMethodFilter
import java.lang.reflect.Method

class RequestApiVersionMethodFilter(
    private val allowedApiVersions: Set<ApiVersion>,
) : OpenApiMethodFilter {
    override fun isMethodToInclude(method: Method): Boolean {
        val requestApiVersion =
            method.findMergedAnnotation(RequestApiVersion::class)
                ?: return false

        val methodApiVersions = requestApiVersion.value.map { ApiVersion(it) }.toSet()
        return methodApiVersions.any { it in allowedApiVersions }
    }
}
