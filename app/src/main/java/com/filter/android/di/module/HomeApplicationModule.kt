package com.filter.android.di.module

import android.content.Context
import com.filter.android.app.HomeApplication
import com.filter.android.model.data.AppExecutors
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HomeApplicationModule(private val mHomeApp: HomeApplication) {

    @Singleton
    @Provides
    fun provideApplicationContext(): Context {
        return mHomeApp
    }

    @Singleton
    @Provides
    fun provideInstanceApp(): HomeApplication {
        return mHomeApp
    }

    @Singleton
    @Provides
    fun provideAppExecutors(): AppExecutors {
        return AppExecutors.getInstanceEx()
    }
}