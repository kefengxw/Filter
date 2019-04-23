package com.filter.android.model.remote

object RemoteDataRepositoryFactory {

    @Volatile
    private var mInstanceReposService: RemoteDataRepository? = null

    @Synchronized
    fun getInstanceReposService(remoteDataInfoService: RemoteDataInfoService): RemoteDataRepository {

        if (mInstanceReposService == null) {
            mInstanceReposService = RemoteDataRepository(remoteDataInfoService)
        }
        return mInstanceReposService!!
    }

    fun destroyInstanceReposService() {
        mInstanceReposService = null
    }
}
