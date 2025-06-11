package com.betomorrow.server.spring.mvc.versions

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class RequestApiVersion(
    vararg val value: String,
)
