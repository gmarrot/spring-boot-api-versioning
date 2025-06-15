package com.betomorrow.sandbox.api

import com.betomorrow.sandbox.PathVersioningApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest(classes = [PathVersioningApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal abstract class ApiTest {
    @Autowired
    protected lateinit var mockMvc: MockMvc
}
