package com.filter.android.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    //public T mRepository = null;
    //LiveDataReactiveStreams to be improved
    private lateinit var mDisposable: CompositeDisposable

    init {
        init()
    }

    private fun init() {
        //mRepository = initRepository();
        mDisposable = CompositeDisposable()
    }

    //abstract protected T initRepository();

    protected fun addDisposable(it: Disposable) {
        mDisposable.add(it)
    }

    override fun onCleared() {
        clearDisposable()
        super.onCleared()
    }

    private fun clearDisposable() {
        mDisposable.clear()
    }
}