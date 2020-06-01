package com.example.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contextExample()

    }

    fun sumAll(){
        runBlocking {
            val d1 = async { delay(1000L) ; 1 }
            Log.d("codbs", "after async(d1)")
            val d2 = async { delay(1000L) ; 2 }
            Log.d("codbs", "after async(d2)")
            val d3 = async { delay(1000L) ; 3 }
            Log.d("codbs", "after async(d3)")

            Log.d("codbs", "1+2+3 = ${d1.await() + d2.await() + d3.await()}")
            Log.d("codbs", "after await all & add")
        }
    }
    fun yieldExample(){
        runBlocking {
            launch {
                Log.d("codbs","1")
                yield()
                Log.d("codbs","2")
            }
            launch {
                Log.d("codbs","3")
//                yield()
                Log.d("codbs","4")
            }
            launch {
                Log.d("codbs","5")
                yield()
                Log.d("codbs","6")
            }
        }
    }
    fun contextExample(){
        runBlocking {
            launch{ // 부모 컨텍스트를 사용
                Log.d("codbs", "mainBlocking: I'm working in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Unconfined){ // 특정 스레드에 종속되지 않음
                Log.d("codbs", "mainBlocking: I'm working in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Default){ // 기본 디스패처를 사용
                Log.d("codbs", "mainBlocking: I'm working in thread ${Thread.currentThread().name}")
            }
            launch(newSingleThreadContext("MyOwnThread")){ // 새 스레드를 사용
                Log.d("codbs", "mainBlocking: I'm working in thread ${Thread.currentThread().name}")
            }
        }
    }
}
