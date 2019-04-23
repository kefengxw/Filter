package com.filter.android.model.remote

class Resource<T>(val mStatus: Status, val mData: T, val mMessage: String?) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(data: T, msg: String?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}