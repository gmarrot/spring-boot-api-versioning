package com.betomorrow.sandbox.infra.openapi

import com.betomorrow.sandbox.infra.spring.ApiVersion
import com.betomorrow.sandbox.infra.spring.RequestApiVersion
import com.betomorrow.sandbox.infra.spring.findMergedAnnotation
import org.springdoc.core.filters.OpenApiMethodFilter
import java.lang.reflect.Method

class RequestApiVersionMethodFilter(
    private val allowedApiVersions: Set<ApiVersion>,
) : OpenApiMethodFilter {

    override fun isMethodToInclude(method: Method): Boolean {
        val requestApiVersion = method.findMergedAnnotation(RequestApiVersion::class)
            ?: return false

        val methodApiVersions = requestApiVersion.value.map { ApiVersion(it) }.toSet()
        return methodApiVersions.any { allowedApiVersions.contains(it) }
    }
}
