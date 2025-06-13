package com.betomorrow.server.spring.mvc.versions

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.servlet.mvc.condition.RequestCondition

class ApiVersionHeaderRequestCondition(
    private val apiVersions: Set<ApiVersion>,
) : RequestCondition<ApiVersionHeaderRequestCondition> {
    override fun combine(other: ApiVersionHeaderRequestCondition): ApiVersionHeaderRequestCondition {
        // Override condition with more specific one (method API versions overrides class API versions)
        return ApiVersionHeaderRequestCondition(other.apiVersions)
    }

    override fun compareTo(other: ApiVersionHeaderRequestCondition, request: HttpServletRequest): Int {
        val missingKeys = other.apiVersions.minus(this.apiVersions)
        return missingKeys.size
    }

    override fun getMatchingCondition(request: HttpServletRequest): ApiVersionHeaderRequestCondition? {
        val version = this.getApiVersionFromRequest(request)
        return if (version != null && this.apiVersions.contains(version)) {
            this
        } else {
            null
        }
    }

    private fun getApiVersionFromRequest(request: HttpServletRequest): ApiVersion? {
        try {
            val version = request.getHeader(API_VERSION_HEADER_KEY) ?: return null
            return ApiVersion(version)
        } catch (e: IllegalArgumentException) {
            return null
        }
    }

    companion object {
        private const val API_VERSION_HEADER_KEY = "x-api-version"
    }
}
