package com.betomorrow.sandbox.config

import com.betomorrow.spring.mvc.versions.EnableRequestApiVersion
import com.betomorrow.spring.mvc.versions.RequestApiVersionMode
import org.springframework.context.annotation.Configuration

@Configuration
@EnableRequestApiVersion(
    mode = RequestApiVersionMode.HEADER,
    headerName = "x-api-version",
)
class ApplicationConfiguration
