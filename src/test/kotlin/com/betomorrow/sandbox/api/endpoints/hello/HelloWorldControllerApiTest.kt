package com.betomorrow.sandbox.api.endpoints.hello

import com.betomorrow.sandbox.api.ApiTest
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

internal class HelloWorldControllerApiTest : ApiTest() {
    @Test
    fun `hello world endpoint should not be accessible without API version header`() {
        mockMvc
            .perform(get("/hello"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `hello world endpoint should not be accessible for invalid API version`() {
        mockMvc
            .perform(
                get("/hello")
                    .header("X-Api-Version", "invalid")
            )
            .andExpect(status().isNotFound)
    }

    @Test
    fun `hello world endpoint should be accessible for API version 1`() {
        mockMvc
            .perform(
                get("/hello")
                    .header("X-Api-Version", "1.0")
            )
            .andExpect(status().isOk)
            .andExpect(content().string("Hello World!"))
    }

    @Test
    fun `hello world endpoint should be accessible for API version 2`() {
        mockMvc
            .perform(
                get("/hello")
                    .header("X-Api-Version", "2.0")
            )
            .andExpect(status().isOk)
            .andExpect(content().string("Hello World!"))
    }

    @Test
    fun `hello endpoint with name param should not be accessible without API version header`() {
        mockMvc
            .perform(get("/hello/Bob"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `hello endpoint with name param should not be accessible for invalid API version`() {
        mockMvc
            .perform(
                get("/hello/Bob")
                    .header("X-Api-Version", "invalid")
            )
            .andExpect(status().isNotFound)
    }

    @Test
    fun `hello endpoint with name param should be accessible for API version 1`() {
        mockMvc
            .perform(
                get("/hello/Bob")
                    .header("X-Api-Version", "1.0")
            )
            .andExpect(status().isOk)
            .andExpect(content().string("Hello Bob"))
    }

    @Test
    fun `hello endpoint with name param should not be accessible for API version 2`() {
        mockMvc
            .perform(
                get("/hello/Bob")
                    .header("X-Api-Version", "2.0")
            )
            .andExpect(status().isNotFound)
    }
}
