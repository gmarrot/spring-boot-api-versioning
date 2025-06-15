package com.betomorrow.sandbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class HeaderVersioningApplication

fun main(args: Array<String>) {
    runApplication<HeaderVersioningApplication>(*args)
}
