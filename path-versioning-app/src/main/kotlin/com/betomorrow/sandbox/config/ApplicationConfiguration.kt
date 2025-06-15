package com.betomorrow.sandbox.config

import com.betomorrow.spring.mvc.versions.EnableRequestApiVersion
import com.betomorrow.spring.mvc.versions.RequestApiVersionMode
import org.springframework.context.annotation.Configuration

@Configuration
@EnableRequestApiVersion(
    mode = RequestApiVersionMode.PATH,
    pathVariableName = "{api-version}",
    pathVersionPrefix = "v",
)
class ApplicationConfiguration
