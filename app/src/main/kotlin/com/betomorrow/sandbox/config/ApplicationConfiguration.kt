package com.betomorrow.sandbox.config

import com.betomorrow.spring.mvc.versions.EnableRequestApiVersion
import org.springframework.context.annotation.Configuration

@Configuration
@EnableRequestApiVersion(
    headerName = "x-api-version",
)
class ApplicationConfiguration
