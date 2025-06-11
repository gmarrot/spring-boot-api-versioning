package com.betomorrow.sandbox.api.endpoints.users

import com.betomorrow.server.spring.mvc.versions.RequestApiVersion
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
@RequestApiVersion("2.0")
class UserController {
    @GetMapping
    fun listUsers(): UserDtos.UsersResponse {
        val users = listOf(
            UserDtos.User("foobar@example.com", true),
            UserDtos.User("john.doe@example.com", false),
        )
        return UserDtos.UsersResponse(users)
    }
}
