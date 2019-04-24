package com.filter.android.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.filter.android.app.HomeApplication
import com.filter.android.di.component.HomeApplicationComponent

abstract class BaseActivity : AppCompatActivity() {//extends DaggerAppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ButterKnife or improve the Dagger2, just reserved for expand.
    }

    fun getApplicationComponent(): HomeApplicationComponent {
        return (application as HomeApplication).getApplicationComponent()
    }
}