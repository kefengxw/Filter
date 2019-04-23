package com.filter.android.model.repository

import com.filter.android.model.data.AppExecutors
import com.filter.android.model.remote.RemoteDataRepository

object DataRepositoryFactory {

    @Volatile
    private var mInstanceRepos: DataRepository? = null

    @Synchronized
    fun getInstanceRepos(remote: RemoteDataRepository, appExecutors: AppExecutors): DataRepository {

        if (mInstanceRepos == null) {
            mInstanceRepos = DataRepository(remote, appExecutors)
        }
        return mInstanceRepos!!
    }

    fun destroyInstanceRepos() {
        mInstanceRepos = null
    }
}