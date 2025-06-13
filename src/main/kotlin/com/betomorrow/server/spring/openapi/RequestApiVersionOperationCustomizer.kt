package com.betomorrow.server.spring.openapi

import com.betomorrow.server.spring.findMergedAnnotation
import com.betomorrow.server.spring.mvc.versions.RequestApiVersion
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.media.StringSchema
import io.swagger.v3.oas.models.parameters.HeaderParameter
import org.springdoc.core.customizers.GlobalOperationCustomizer
import org.springframework.web.method.HandlerMethod

class RequestApiVersionOperationCustomizer(
    private val headerName: String,
) : GlobalOperationCustomizer {
    override fun customize(operation: Operation, handlerMethod: HandlerMethod): Operation {
        val requestApiVersion = handlerMethod.method.findMergedAnnotation(RequestApiVersion::class)
        if (requestApiVersion != null) {
            operation.addParametersItem(
                HeaderParameter()
                    .name(headerName)
                    .schema(requestApiVersion.toOpenApiSchema())
                    .required(true),
            )
        }
        return operation
    }

    private fun RequestApiVersion.toOpenApiSchema(): Schema<String> {
        return StringSchema()
            .apply {
                value.forEach { addEnumItem(it) }
            }
    }
}
