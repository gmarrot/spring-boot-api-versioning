package com.betomorrow.sandbox.api.endpoints.users

import com.betomorrow.sandbox.api.ApiTest
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

internal class UserControllerApiTest : ApiTest() {
    @Test
    fun `list users endpoint should not be accessible for API version 2`() {
        mockMvc
            .perform(
                get("/users")
                    .header("X-Api-Version", "2.0")
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.users[0].email", equalTo("foobar@example.com")))
            .andExpect(jsonPath("$.users[1].email", equalTo("john.doe@example.com")))
    }
}
