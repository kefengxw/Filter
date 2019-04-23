package com.filter.android.model.remote

import com.filter.android.model.data.AppExecutors

object RemoteDataInfoServiceFactory {

    @Volatile
    private var mInstanceService: RemoteDataInfoService? = null

    @Synchronized
    fun getInstanceService(appExecutors: AppExecutors): RemoteDataInfoService {

        if (mInstanceService == null) {
            mInstanceService = RetrofitClient.createService(RemoteDataInfoService::class.java, appExecutors)
        }
        return mInstanceService!!
    }

    fun destroyInstanceService() {
        mInstanceService = null
    }
}