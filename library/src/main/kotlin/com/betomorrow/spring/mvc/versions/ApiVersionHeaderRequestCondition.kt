package com.betomorrow.spring.mvc.versions

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.servlet.mvc.condition.RequestCondition

class ApiVersionHeaderRequestCondition(
    private val apiVersions: Set<ApiVersion>,
    private val headerName: String,
) : RequestCondition<ApiVersionHeaderRequestCondition> {
    override fun combine(other: ApiVersionHeaderRequestCondition): ApiVersionHeaderRequestCondition {
        // Override condition with more specific one (method API versions overrides class API versions)
        return ApiVersionHeaderRequestCondition(other.apiVersions, headerName)
    }

    override fun compareTo(other: ApiVersionHeaderRequestCondition, request: HttpServletRequest): Int {
        val missingKeys = other.apiVersions.minus(this.apiVersions)
        return missingKeys.size
    }

    override fun getMatchingCondition(request: HttpServletRequest): ApiVersionHeaderRequestCondition? {
        val version = this.getApiVersionFromRequest(request)
        return if (version != null && version in this.apiVersions) {
            this
        } else {
            null
        }
    }

    private fun getApiVersionFromRequest(request: HttpServletRequest): ApiVersion? {
        try {
            val version = request.getHeader(headerName) ?: return null
            return ApiVersion(version)
        } catch (_: IllegalArgumentException) {
            return null
        }
    }
}
