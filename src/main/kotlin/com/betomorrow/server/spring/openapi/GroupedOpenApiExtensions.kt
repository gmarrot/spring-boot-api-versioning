package com.betomorrow.server.spring.openapi

import com.betomorrow.server.spring.mvc.versions.ApiVersion
import org.springdoc.core.GroupedOpenApi

fun GroupedOpenApi.Builder.apiVersionsToMatch(vararg version: String): GroupedOpenApi.Builder = addOpenApiMethodFilter(
    RequestApiVersionMethodFilter(version.map { ApiVersion(it) }.toSet())
)
