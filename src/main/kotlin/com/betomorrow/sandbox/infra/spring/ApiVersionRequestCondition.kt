package com.betomorrow.sandbox.infra.spring

import org.springframework.web.servlet.mvc.condition.RequestCondition
import javax.servlet.http.HttpServletRequest

class ApiVersionRequestCondition(
    private val apiVersions: Set<ApiVersion>,
) : RequestCondition<ApiVersionRequestCondition> {
    override fun combine(other: ApiVersionRequestCondition): ApiVersionRequestCondition {
        // Override condition with more specific one (method API versions overrides class API versions)
        return ApiVersionRequestCondition(other.apiVersions)
    }

    override fun compareTo(other: ApiVersionRequestCondition, request: HttpServletRequest): Int {
        val missingKeys = other.apiVersions.minus(this.apiVersions)
        return missingKeys.size
    }

    override fun getMatchingCondition(request: HttpServletRequest): ApiVersionRequestCondition? {
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
