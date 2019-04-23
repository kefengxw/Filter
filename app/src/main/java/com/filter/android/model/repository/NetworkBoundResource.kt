package com.filter.android.model.repository

import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.filter.android.model.data.AppExecutors
import com.filter.android.model.remote.Resource
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import retrofit2.Response

//database, network, UI, 3 threads
abstract class NetworkBoundResource<ResultType, RequestType, RequestToken> @MainThread constructor(private val mEx: AppExecutors) {

    private val mResult = PublishSubject.create<Resource<ResultType>>()

    init {
        //all logical control here, local first, remote first, or fetch every time
        loadData()
    }

    private fun loadData() {
        @Suppress("UNCHECKED_CAST")
        mResult.onNext(Resource.loading(null as ResultType))
        if (shouldGetRemoteToken()) {
            fetchRemoteToken()
        } else {
            fetchFromNetwork()
        }
    }

    private fun fetchRemoteToken() {
        createTokenCall()
            .subscribeOn(mEx.asRxSchedulerNetwork())
            .observeOn(mEx.asRxSchedulerMainThread())
            .subscribe(object : SingleObserver<Response<RequestToken>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(response: Response<RequestToken>) {
                    if (response.isSuccessful) {
                        convertTokenCallResult(processTokenResponse(response)!!)
                        fetchFromNetwork()
                    } else {
                        onFetchTokenFailed()
                    }
                }

                override fun onError(e: Throwable) {
                    onFetchTokenFailed()
                }
            })
    }

    private fun fetchFromNetwork() {
        //Single<ApiResponse<RequestType>> apiResponseSingle =
        createNetworkCall()
            .subscribeOn(mEx.asRxSchedulerNetwork())
            .observeOn(mEx.asRxSchedulerMainThread())
            .subscribe(object : SingleObserver<Response<RequestType>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(response: Response<RequestType>) {
                    if (response.isSuccessful) {
                        val it = convertNetworkCallResult(processNetworkResponse(response)!!)
                        mResult.onNext(Resource.success(it))
                    } else {
                        @Suppress("UNCHECKED_CAST")
                        mResult.onNext(Resource.error(null as ResultType, response.message()))
                    }
                }

                override fun onError(e: Throwable) {
                    @Suppress("UNCHECKED_CAST")
                    mResult.onNext(Resource.error(null as ResultType, e.message))
                    onFetchNetworkFailed()
                }
            })
    }

    // Returns a LiveData object that represents the resource that's implemented in the base class.
    //.startWith(Resource.loading(null));
    fun asFlowable(): Flowable<Resource<ResultType>> {
        return mResult.toFlowable(BackpressureStrategy.BUFFER)
    }

    @WorkerThread
    private fun processNetworkResponse(response: Response<RequestType>): RequestType? {
        return response.body()//return response.getBody();
    }

    @WorkerThread
    private fun processTokenResponse(response: Response<RequestToken>): RequestToken? {
        return response.body()//return response.getBody();
    }

    @WorkerThread
    protected abstract fun convertNetworkCallResult(data: RequestType): ResultType

    @WorkerThread
    protected abstract fun convertTokenCallResult(data: RequestToken)

    @MainThread
    protected abstract fun createNetworkCall(): Single<Response<RequestType>>

    @MainThread
    protected abstract fun shouldGetRemoteToken(): Boolean

    @MainThread
    protected abstract fun createTokenCall(): Single<Response<RequestToken>>

    @MainThread// Called when the fetch fails. The child class may want to reset components like rate limiter.
    protected open fun onFetchNetworkFailed() {/*Log*/
    }

    @MainThread
    protected open fun onFetchTokenFailed() {/*Log*/
    }
}