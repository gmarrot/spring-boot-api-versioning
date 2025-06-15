package com.betomorrow.sandbox.api.endpoints.users

import com.betomorrow.sandbox.api.ApiTest
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

internal class LegacyUserControllerPathVersioningApiTest : ApiTest() {
    @Test
    fun `list users endpoint should not be accessible without API version header`() {
        mockMvc
            .perform(get("/users"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `list users endpoint should not be accessible for invalid API version`() {
        mockMvc
            .perform(get("/invalid/users"))
            .andExpect(status().isNotFound)

        mockMvc
            .perform(get("/vinvalid/users"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `list users endpoint should not be accessible for API version 1`() {
        mockMvc
            .perform(get("/v1.0/users"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].email", equalTo("foobar@example.com")))
            .andExpect(jsonPath("$[1].email", equalTo("john.doe@example.com")))
    }
}
