package com.filter.android.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.filter.android.app.HomeApplication
import com.filter.android.model.data.AppExecutors
import com.filter.android.model.remote.Resource
import com.filter.android.model.repository.DataRepository
import com.filter.android.model.repository.DisplayData
import io.reactivex.Flowable

class RecyListDataViewModel(app: Application) : BaseViewModel(app) {

    private lateinit var mEx: AppExecutors
    private lateinit var mFilterData: LiveData<Resource<DisplayData>>
    private lateinit var mRepos: DataRepository
    private val mFilter = MutableLiveData<String>()
    private var mPrevLength = 0

    init {//can be replace by ViewModel not android view model
        initViewModel(app)
    }

    private fun initViewModel(app: Application) {

        val mInstanceApp = app as HomeApplication//this.getApplication<HomeApplication>()

        mEx = mInstanceApp.getInstanceEx()
        mRepos = mInstanceApp.getInstanceRepos()
        initFilterData()
    }

    private fun initFilterData() {
        mFilterData = Transformations.switchMap(mFilter) { getDataByName(it) }
    }

    fun getDataByFilter(): LiveData<Resource<DisplayData>> {
        return mFilterData
    }

    fun setFilter(input: String) {

        val it = input.trim()
        val tmp = it.length

        if (tmp > 0 || mPrevLength > 0) {//only handle valid input, all logical is done by ViewModel
            mFilter.value = it//Improvement 1: To avoid subscribe and unsubscribe each time
        }
        mPrevLength = tmp
    }

    private fun getDataByName(input: String): LiveData<Resource<DisplayData>> {
        //all the logical is done by ViewModel
        //Flowable<Resource<DisplayData>> (input.toLowerCase() + "%");
        val it = mRepos.getDataByName(input.toLowerCase(), "artist", "SE")
        return LiveDataReactiveStreams.fromPublisher(it)
    }

    override fun onCleared() {
        super.onCleared()
    }
}