package com.betomorrow.spring

import org.springframework.core.annotation.AnnotationUtils
import java.lang.reflect.Method
import kotlin.reflect.KClass

fun <T : Annotation> Method.findMergedAnnotation(annotationType: KClass<T>): T? =
    AnnotationUtils.findAnnotation(this, annotationType.java)
        ?: AnnotationUtils.findAnnotation(this.declaringClass, annotationType.java)
