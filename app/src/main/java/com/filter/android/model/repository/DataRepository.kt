package com.filter.android.model.repository

import com.filter.android.model.data.AppExecutors
import com.filter.android.model.data.InternalDataConfiguration.INIT_VISIT_TOKEN
import com.filter.android.model.data.InternalDataConfiguration.VISIT_TOKEN
import com.filter.android.model.data.InternalDataConfiguration.updateToken
import com.filter.android.model.remote.*
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response

class DataRepository(remote: RemoteDataRepository, appExecutors: AppExecutors) {

    private val mEx: AppExecutors = appExecutors
    private val mRemoteDataRepository: RemoteDataRepository = remote
    private var mResultDisplay = DisplayData()

    @Suppress("UNUSED_PARAMETER")
    fun getDataByName(
        name: String,
        type: String,
        market: String,
        limit: Int = 10,
        offset: Int = 5
    ): Flowable<Resource<DisplayData>> {

        val nBResource = object : NetworkBoundResource<DisplayData, RemoteBean, RemoteToken>(mEx) {

            override fun createNetworkCall(): Single<Response<RemoteBean>> {
                return mRemoteDataRepository.getRemoteInfo(name, type, market, limit, offset)
            }

            override fun convertNetworkCallResult(data: RemoteBean): DisplayData {
                return convertCallResultToDisplayData(data)
            }

            override fun shouldGetRemoteToken(): Boolean {
                return (VISIT_TOKEN == INIT_VISIT_TOKEN)
            }

            override fun createTokenCall(): Single<Response<RemoteToken>> {
                return mRemoteDataRepository.getRemoteToken()
            }

            override fun convertTokenCallResult(data: RemoteToken) {
                updateToken(data.accessToken)
            }
        }

        return nBResource.asFlowable()
    }

    private fun convertCallResultToDisplayData(data: RemoteBean): DisplayData {

        val artists = data.artists

        //for next query
        mResultDisplay.item = null

        if (artists.total == 0 || artists.items == null || artists.items.isEmpty()) {
            return mResultDisplay
        }

        val tmpList = ArrayList<Item>()
        val eachArtist: List<ArtistItem> = artists.items

        for (item in eachArtist) {
            item.images?.let {
                if (!(it.isNullOrEmpty())) {
                    tmpList.add(Item(item.name, it.first().url))
                }
            }
        }

        mResultDisplay.total = artists.total
        mResultDisplay.item = tmpList

        return mResultDisplay;
    }
}