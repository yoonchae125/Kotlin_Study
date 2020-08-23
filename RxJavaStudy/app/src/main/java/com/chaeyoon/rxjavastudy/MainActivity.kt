package com.chaeyoon.rxjavastudy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.math.BigInteger
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factorials = io.reactivex.rxjava3.core.Observable
            .range(2, 100)
            .scan(BigInteger.ONE, { big, cur ->
                big.multiply(BigInteger.valueOf(cur.toLong()))
            })

        factorials.subscribe { Log.d("codbs", it.toString()) }

        val source = Observable.create<CharSequence> { emitter ->
            et.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    s?.let {
                        emitter.onNext(it)
//                        tv_hello.text = ""
//                        val dan = s.toString().toInt()
//                        Observable.range(1,9)
//                            .map {
//                                "$dan x $it = ${dan*it}"
//                            }
//                            .reduce{x,y ->
//                                "$x\n$y"
//                            }
//                            .subscribe{
//                                tv_hello.text = it
//                            }
                    }

                }
            })
        }
        source.debounce(3000L, TimeUnit.MILLISECONDS)
            .filter { !TextUtils.isEmpty(it) }
            .observeOn(AndroidSchedulers.mainThread()) //Toast must be running on UI Thread
            .subscribe {
                Toast.makeText(this, "searching => $it", Toast.LENGTH_SHORT).show()
            }
        Observable.just(tv_hello.text)
            .map {
                " RX!"
            }
            .subscribe {
                tv_hello.text = it
            }
        val url1 = "https://raw.githubusercontent.com/Charlezz/RxJavaStudy/master/Sample/first.txt"
        val url2 = "https://raw.githubusercontent.com/Charlezz/RxJavaStudy/master/Sample/second.txt"
        val client = OkHttpClient()

        val request1 = Request.Builder().url(url1).build()
        val request2 = Request.Builder().url(url2).build()

        Observable.create<View> { button1.setOnClickListener(it::onNext) }
            .subscribe {
                client.newCall(request1).enqueue(object : Callback {

                    override fun onFailure(call: Call, e: IOException) {
                        Log.d("", e?.message)
                    }

                    override fun onResponse(call: Call, response: Response) {

                        Log.d("codbs", response?.body?.string())
                    }
                })
            }


        button2.setOnClickListener {
            client.newCall(request2).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    Log.d("", e?.message)
                }

                override fun onResponse(call: Call, response: Response) {

                    Log.d("codbs", response?.body?.string())
                }
            })
        }
        val src1 = Observable.just(url1).subscribeOn(Schedulers.io()).map(::get)
        val src2 = Observable.just(url2).subscribeOn(Schedulers.io()).map(::get)
        val start = System.currentTimeMillis()

        button3.setOnClickListener {
            Observable.concat(src1, src2)
                .subscribe {
                    Log.d("codbs", it)
                }
        }

        page.setOnClickListener {
            val intent = Intent(this, Page1Activity::class.java)
            startActivity(intent)
        }
        retrofitTest.setOnClickListener{
            val intent = Intent(this, RetrofitTestActivity::class.java)
            startActivity(intent)
        }
    }

    fun get(url: String): String? {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()
        return try {
            client.newCall(request).execute().body?.string()
        } catch (e: Exception) {
            e.message
        }
    }
}