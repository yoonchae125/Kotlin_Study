package com.chaeyoon.rxjavastudy.utils

import android.util.Log
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class BackPressureSample {
    fun overBackPressure(){
        Flowable
            .range(1, 999999999)
            .onBackpressureDrop()
            .doOnNext{ Log.d("codbs", "send event $it")}
            .observeOn(Schedulers.computation())
            .subscribe{
                Thread.sleep(30)
                Log.d("codbs", "receive event $it")
            }
    }
}