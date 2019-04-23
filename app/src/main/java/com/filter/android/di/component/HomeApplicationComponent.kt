package com.filter.android.di.component

import com.filter.android.app.HomeApplication
import com.filter.android.di.module.ActivityModule
import com.filter.android.di.module.HomeApplicationModule
import com.filter.android.di.module.RemoteModule
import com.filter.android.di.module.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        HomeApplicationModule::class,
        RemoteModule::class,
        RepositoryModule::class,
        ActivityModule::class]
)
interface HomeApplicationComponent { //extends AndroidInjector<HomeApplication>

    fun inject(homeApplication: HomeApplication)

    fun homeActivityComponent(): HomeActivityComponent.Builder
}