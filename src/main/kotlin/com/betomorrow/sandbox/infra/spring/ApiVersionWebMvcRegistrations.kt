package com.betomorrow.sandbox.infra.spring

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@Component
class ApiVersionWebMvcRegistrations : WebMvcRegistrations {
    override fun getRequestMappingHandlerMapping(): RequestMappingHandlerMapping {
        return ApiVersionRequestMappingHandlerMapping()
    }
}
