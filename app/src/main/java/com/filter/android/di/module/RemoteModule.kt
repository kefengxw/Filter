package com.filter.android.di.module

import com.filter.android.model.data.AppExecutors
import com.filter.android.model.remote.RemoteDataInfoService
import com.filter.android.model.remote.RemoteDataInfoServiceFactory
import com.filter.android.model.remote.RemoteDataRepository
import com.filter.android.model.remote.RemoteDataRepositoryFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule {

    @Singleton
    @Provides
    fun provideRemoteDataInfoService(appExecutors: AppExecutors): RemoteDataInfoService {
        return RemoteDataInfoServiceFactory.getInstanceService(appExecutors)
    }

    @Singleton
    @Provides
    fun provideRemoteDataRepository(remoteDataInfoService: RemoteDataInfoService): RemoteDataRepository {
        return RemoteDataRepositoryFactory.getInstanceReposService(remoteDataInfoService)
    }
}