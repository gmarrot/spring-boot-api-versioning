package com.betomorrow.server.spring.mvc.versions

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@Component
class ApiVersionWebMvcRegistrations : WebMvcRegistrations {
    override fun getRequestMappingHandlerMapping(): RequestMappingHandlerMapping {
        return ApiVersionHeaderRequestMappingHandlerMapping()
    }
}
