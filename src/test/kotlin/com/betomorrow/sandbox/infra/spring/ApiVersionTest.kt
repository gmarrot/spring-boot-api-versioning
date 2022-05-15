package com.betomorrow.sandbox.infra.spring

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ApiVersionTest {
    @Test
    fun `test equality`() {
        assertThat(ApiVersion("1")).isEqualTo(ApiVersion("1"))
        assertThat(ApiVersion("1")).isNotEqualTo(ApiVersion("1.1"))
        assertThat(ApiVersion("1.1")).isNotEqualTo(ApiVersion("1.1.1"))
        assertThat(ApiVersion("1")).isNotEqualTo(ApiVersion("2"))
    }

    @Test
    fun `invalid version format should throw at instantiation`() {
        assertThrows<IllegalArgumentException> { ApiVersion("") }
        assertThrows<IllegalArgumentException> { ApiVersion(".") }
        assertThrows<IllegalArgumentException> { ApiVersion("a.b") }
        assertThrows<IllegalArgumentException> { ApiVersion("1.0.0.0") }
    }
}
