package com.betomorrow.spring.mvc.versions

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

class ApiVersionHeaderWebMvcRegistrations(
    private val headerName: String,
) : WebMvcRegistrations {
    override fun getRequestMappingHandlerMapping(): RequestMappingHandlerMapping {
        return ApiVersionHeaderRequestMappingHandlerMapping(headerName)
    }
}

class ApiVersionPathWebMvcRegistrations(
    private val pathVariableName: String,
    private val pathVersionPrefix: String,
) : WebMvcRegistrations {
    override fun getRequestMappingHandlerMapping(): RequestMappingHandlerMapping {
        return ApiVersionPathRequestMappingHandlerMapping(
            pathVariableName = pathVariableName,
            pathVersionPrefix = pathVersionPrefix,
        )
    }
}
