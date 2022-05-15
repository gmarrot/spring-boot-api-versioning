package com.betomorrow.sandbox.infra.spring

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class RequestApiVersion(
    vararg val value: String,
)
