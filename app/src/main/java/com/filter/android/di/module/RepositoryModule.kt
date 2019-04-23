package com.filter.android.di.module

import com.filter.android.model.data.AppExecutors
import com.filter.android.model.remote.RemoteDataRepository
import com.filter.android.model.repository.DataRepository
import com.filter.android.model.repository.DataRepositoryFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideDataRepository(remote: RemoteDataRepository, appExecutors: AppExecutors): DataRepository {
        return DataRepositoryFactory.getInstanceRepos(remote, appExecutors)
    }
}