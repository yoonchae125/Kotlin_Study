package com.chaeyoon.rxjavastudy

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.activity_page1.*
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit

class Page1Activity : AppCompatActivity() {
    private val blob = ByteArray(32 * 1024 * 1024)
    private lateinit var disposable: Disposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page1)
        // 1. Activity가 생성되고, onCreate()에서 interval()을 구독한다
        // 2. 100ms 마다 text가 현재 카운터 값으로 갱신된다.
        // 3. 장치의 방향이 바뀐다
        // 4. Activity가 종료되고 새로이 생성되며 onCreate가 다시 실행된다
        // 5. 이제 Observable.interval() 두 개가 싱행 중이다. -> 첫 번쨰 구독을 해지하지 않았기 때문이
        // 해결책 : interval()가 더 이상 필요하지 않을 때 이를 구독 해지하여 알리면 된다

        disposable = Observable
            .interval(100, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                textView.text = it.toString()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}