package com.betomorrow.sandbox.api.endpoints.hello

import com.betomorrow.sandbox.infra.spring.RequestApiVersion
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello")
@RequestApiVersion("1.0", "2.0")
class HelloWorldController {
    @GetMapping
    fun helloWorld(): String {
        return "Hello World!"
    }

    @GetMapping("/{name}")
    @RequestApiVersion("1.0")
    fun hello(@PathVariable name: String): String {
        return "Hello $name"
    }
}
