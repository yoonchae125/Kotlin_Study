package com.chaeyoon.rxjavastudy.utils

import android.util.Log
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers

// sample, debounce, buffer

class BackPressureSample {

    fun overBackPressure() {
        Flowable
            .range(1, 999999999)
            .onBackpressureDrop()
            .doOnNext { Log.d(TAG, "send event $it") }
            .observeOn(Schedulers.computation())
            .subscribe {
                Thread.sleep(30)
                Log.d(TAG, "receive event $it")
            }
    }

    companion object {
        const val TAG = "BackPressureSample"
    }
}