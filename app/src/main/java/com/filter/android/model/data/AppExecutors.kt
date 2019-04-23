package com.filter.android.model.data

import android.os.Handler
import android.os.Looper
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors(private val diskIO: Executor, val networkIO: Executor, val mainThread: Executor) {

    companion object {

        private var mInstanceEx: AppExecutors? = null

        fun getInstanceEx(): AppExecutors {
            if (mInstanceEx == null) {
                mInstanceEx = AppExecutors(
                    Executors.newSingleThreadExecutor(),
                    Executors.newFixedThreadPool(1),
                    MainThreadExecutor()
                )
            }
            return mInstanceEx!!
        }

        fun destroyInstanceEx() {
            mInstanceEx = null
        }
    }

    private class MainThreadExecutor : Executor {
        private val mMainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mMainThreadHandler.post(command)
        }
    }

    fun asRxSchedulerDiskIO(): Scheduler {
        return Schedulers.from(diskIO)
    }

    fun asRxSchedulerNetwork(): Scheduler {
        return Schedulers.from(networkIO)
    }

    fun asRxSchedulerMainThread(): Scheduler {
        return Schedulers.from(mainThread)
    }

    fun runOnDiskIO(it: Runnable) {
        diskIO.execute(it)
    }

    fun runOnNetwork(it: Runnable) {
        networkIO.execute(it)
    }

    fun runOnMainThread(it: Runnable) {
        mainThread.execute(it)
    }
}