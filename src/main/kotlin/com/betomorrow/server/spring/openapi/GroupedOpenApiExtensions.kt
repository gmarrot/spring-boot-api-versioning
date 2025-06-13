package com.betomorrow.server.spring.openapi

import com.betomorrow.server.spring.mvc.versions.ApiVersion
import org.springdoc.core.models.GroupedOpenApi

fun GroupedOpenApi.Builder.apiVersionsToMatch(vararg version: String): GroupedOpenApi.Builder = addOpenApiMethodFilter(
    RequestApiVersionMethodFilter(version.map { ApiVersion(it) }.toSet()),
)
