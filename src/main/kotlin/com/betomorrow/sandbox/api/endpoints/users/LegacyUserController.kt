package com.betomorrow.sandbox.api.endpoints.users

import com.betomorrow.sandbox.infra.spring.RequestApiVersion
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
@RequestApiVersion("1.0")
class LegacyUserController {
    @GetMapping
    fun listUsers(): List<UserDtos.User> {
        return listOf(
            UserDtos.User("foobar@example.com", true),
            UserDtos.User("john.doe@example.com", false),
        )
    }
}
