package com.filter.android.di.module

import com.filter.android.di.scope.ActivityScope
import com.filter.android.view.HomeActivity

import dagger.Module
import dagger.Provides

@Module
class HomeActivityModule(private val mCountryActivity: HomeActivity) {

    @ActivityScope
    @Provides
    fun provideCountryActivity(): HomeActivity {
        return mCountryActivity
    }
}