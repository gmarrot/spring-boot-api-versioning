package com.betomorrow.spring.mvc.versions

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

class ApiVersionWebMvcRegistrations(
    private val headerName: String,
) : WebMvcRegistrations {
    override fun getRequestMappingHandlerMapping(): RequestMappingHandlerMapping {
        return ApiVersionHeaderRequestMappingHandlerMapping(headerName)
    }
}
