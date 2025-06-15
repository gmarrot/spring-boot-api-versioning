package com.betomorrow.sandbox.api.endpoints.users

interface UserDtos {
    data class User(
        val email: String,
        val enabled: Boolean,
    )

    data class UsersResponse(
        val users: List<User>,
    )
}
