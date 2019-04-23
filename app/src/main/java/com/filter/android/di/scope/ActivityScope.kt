package com.filter.android.di.scope

import javax.inject.Scope

@Scope
//@Retention(RetentionPolicy.RUNTIME)
//@kotlin.annotation.Retention()//default is AnnotationRetention.RUNTIME
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope