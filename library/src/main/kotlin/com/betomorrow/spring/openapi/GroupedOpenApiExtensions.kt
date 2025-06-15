package com.betomorrow.spring.openapi

import com.betomorrow.spring.mvc.versions.ApiVersion
import org.springdoc.core.models.GroupedOpenApi

fun GroupedOpenApi.Builder.apiVersionsToMatch(vararg version: String): GroupedOpenApi.Builder =
    addOpenApiMethodFilter(
        ApiVersionHeaderMethodFilter(version.map { ApiVersion(it) }.toSet()),
    )
