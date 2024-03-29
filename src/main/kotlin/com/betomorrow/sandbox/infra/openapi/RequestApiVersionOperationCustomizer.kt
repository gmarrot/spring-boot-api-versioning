package com.betomorrow.sandbox.infra.openapi

import com.betomorrow.sandbox.infra.spring.RequestApiVersion
import com.betomorrow.sandbox.infra.spring.findMergedAnnotation
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.media.StringSchema
import io.swagger.v3.oas.models.parameters.HeaderParameter
import org.springdoc.core.customizers.GlobalOperationCustomizer
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod

@Component
class RequestApiVersionOperationCustomizer : GlobalOperationCustomizer {
    override fun customize(operation: Operation, handlerMethod: HandlerMethod): Operation {
        val requestApiVersion = handlerMethod.method.findMergedAnnotation(RequestApiVersion::class)
        if (requestApiVersion != null) {
            operation.addParametersItem(
                HeaderParameter()
                    .name("x-api-version")
                    .schema(requestApiVersion.toOpenApiSchema())
                    .required(true)
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
