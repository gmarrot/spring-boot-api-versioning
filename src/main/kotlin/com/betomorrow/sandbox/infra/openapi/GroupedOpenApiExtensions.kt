package com.betomorrow.sandbox.infra.openapi

import com.betomorrow.sandbox.infra.spring.ApiVersion
import org.springdoc.core.GroupedOpenApi

fun GroupedOpenApi.Builder.apiVersionsToMatch(vararg version: String): GroupedOpenApi.Builder = addOpenApiMethodFilter(
    RequestApiVersionMethodFilter(version.map { ApiVersion(it) }.toSet())
)
