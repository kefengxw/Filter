package com.filter.android.di.module

import com.filter.android.di.component.HomeActivityComponent

import dagger.Module

@Module(subcomponents = [HomeActivityComponent::class])//if more activity, please add here
class ActivityModule//Only designed for sub-component