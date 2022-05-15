package com.betomorrow.sandbox.infra.spring

class ApiVersion(version: String) {
    val value = version.trim()

    init {
        if (!isValid(version)) {
            throw IllegalArgumentException("Invalid API version '$version'")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ApiVersion) return false
        return value.equals(other.value, ignoreCase = true)
    }

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = value

    companion object {
        private const val VERSION_REGEX = "^\\d+(\\.\\d+){0,2}$"

        fun isValid(version: String): Boolean {
            return version.isNotBlank() && VERSION_REGEX.toRegex().matches(version)
        }
    }
}
