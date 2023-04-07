package com.betomorrow.sandbox.api.endpoints.users

import com.betomorrow.sandbox.api.ApiTest
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

internal class LegacyUserControllerApiTest : ApiTest() {
    @Test
    fun `list users endpoint should not be accessible without API version header`() {
        mockMvc
            .perform(get("/users"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `list users endpoint should not be accessible for invalid API version`() {
        mockMvc
            .perform(
                get("/users")
                    .header("X-Api-Version", "invalid")
            )
            .andExpect(status().isNotFound)
    }

    @Test
    fun `list users endpoint should not be accessible for API version 1`() {
        mockMvc
            .perform(
                get("/users")
                    .header("X-Api-Version", "1.0")
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].email", equalTo("foobar@example.com")))
            .andExpect(jsonPath("$[1].email", equalTo("john.doe@example.com")))
    }
}
