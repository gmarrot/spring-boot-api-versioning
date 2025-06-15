package com.betomorrow.spring.mvc.versions

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class ApiVersionTest {
    @Test
    fun `test equality`() {
        assertEquals(ApiVersion("1"), ApiVersion("1"))
        assertNotEquals(ApiVersion("1"), ApiVersion("1.1"))
        assertNotEquals(ApiVersion("1.1"), ApiVersion("1.1.1"))
        assertNotEquals(ApiVersion("1"), ApiVersion("2"))
    }

    @Test
    fun `invalid version format should throw at instantiation`() {
        assertThrows<IllegalArgumentException> { ApiVersion("") }
        assertThrows<IllegalArgumentException> { ApiVersion(".") }
        assertThrows<IllegalArgumentException> { ApiVersion("a.b") }
        assertThrows<IllegalArgumentException> { ApiVersion("1.0.0.0") }
    }
}
