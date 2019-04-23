package com.filter.android.model.remote

import com.filter.android.model.data.ExternalDataConfiguration.CLIENT_ID
import com.filter.android.model.data.ExternalDataConfiguration.CLIENT_SECRET
import com.filter.android.model.data.ExternalDataConfiguration.GRANT_TYPE
import com.filter.android.model.data.InternalDataConfiguration.VISIT_TOKEN
import io.reactivex.Single
import retrofit2.Response

class RemoteDataRepository(remoteService: RemoteDataInfoService) {

    private var mDataInfoService: RemoteDataInfoService = remoteService

    fun getRemoteInfo(
        name: String,
        type: String,
        market: String,
        limit: Int,
        offset: Int
    ): Single<Response<RemoteBean>> {
        return mDataInfoService.getRemoteInfo("Bearer ${VISIT_TOKEN}", name, type, market, limit, offset)
    }

    //fun getRemoteToken(body: String = "client_credentials"): Single<Response<RemoteToken>> {
    fun getRemoteToken(): Single<Response<RemoteToken>> {
        return mDataInfoService.getRemoteToken(GRANT_TYPE, CLIENT_ID, CLIENT_SECRET)
    }
}