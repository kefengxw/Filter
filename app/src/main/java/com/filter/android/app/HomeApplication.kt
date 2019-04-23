package com.filter.android.app

import com.filter.android.di.component.DaggerHomeApplicationComponent
import com.filter.android.di.component.HomeApplicationComponent
import com.filter.android.di.module.HomeApplicationModule
import com.filter.android.model.data.AppExecutors
import com.filter.android.model.remote.RemoteDataInfoService
import com.filter.android.model.remote.RemoteDataRepository
import com.filter.android.model.repository.DataRepository
import javax.inject.Inject

class HomeApplication : BaseApplication() {

    private lateinit var mInstanceApp: HomeApplication
    private lateinit var mInstanceEx: AppExecutors
    private lateinit var mInstanceService: RemoteDataInfoService
    private lateinit var mInstanceReposService: RemoteDataRepository
    private lateinit var mInstanceRepos: DataRepository
    private lateinit var mApplicationComponent: HomeApplicationComponent

    override fun onCreate() {
        super.onCreate()
        init()
        //this.registerActivityLifecycleCallbacks();
    }

    private fun init() {
        initInjector()//Inject
    }

    private fun initInjector() {
        mApplicationComponent = DaggerHomeApplicationComponent.builder()
            .homeApplicationModule(HomeApplicationModule(this))
            .build()
        mApplicationComponent.inject(this)
    }

    fun getApplicationComponent(): HomeApplicationComponent {
        return mApplicationComponent
    }

    @Inject
    fun setInstanceApp(instanceApp: HomeApplication) {
        this.mInstanceApp = instanceApp
    }

    @Inject
    fun setAppExecutors(appExecutors: AppExecutors) {
        mInstanceEx = appExecutors
    }

    @Inject
    fun setInstanceService(instanceService: RemoteDataInfoService) {
        mInstanceService = instanceService
    }

    @Inject
    fun setInstanceReposService(instanceReposService: RemoteDataRepository) {
        mInstanceReposService = instanceReposService
    }

    @Inject
    fun setInstanceRepos(instanceRepos: DataRepository) {
        mInstanceRepos = instanceRepos
    }

    fun getInstanceApp(): HomeApplication {
        return mInstanceApp
    }

    fun getInstanceEx(): AppExecutors {
        return mInstanceEx
    }

    fun getInstanceService(): RemoteDataInfoService {
        return mInstanceService
    }

    fun getInstanceRepos(): DataRepository {
        return mInstanceRepos
    }

    fun getInstanceReposService(): RemoteDataRepository {
        return mInstanceReposService
    }

    override fun onLowMemory() {
        super.onLowMemory()
        //log here, in case too much info
    }
}