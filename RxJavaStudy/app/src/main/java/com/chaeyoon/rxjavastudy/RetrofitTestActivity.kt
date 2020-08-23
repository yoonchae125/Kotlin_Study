package com.chaeyoon.rxjavastudy

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_retrofit_test.*
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RetrofitTestActivity : AppCompatActivity() {
    private val objectMapper = ObjectMapper()
    private lateinit var meetup: MeetupApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_test)

        // jackson library에서 ObjectMapper를 조정해 언더바로 연결된 이름을 는
        // 자바빈에서 사용하는 카멜케이스 규칙으로 변환한다
        // 예를 들어 JSON의 localized_country_name 은
        // java에서 localizedCountryName이어야 한다
        objectMapper.propertyNamingStrategy =
            PropertyNamingStrategy.SNAKE_CASE

        // bean class에 매핑되지 않은 필드는 피하고 싶다
        objectMapper.configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false
        )
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.meetup.com/")
            .addCallAdapterFactory(
                RxJava3CallAdapterFactory.create()
            )
            .addConverterFactory(
                JacksonConverterFactory.create(objectMapper)
            )
            .build()
        // Retrofit 객체를 적용
        meetup = retrofit.create(MeetupApi::class.java)
        // meetup api를 통해 특정 위치 근처의 모든 도시 목록을 가져온다
        val warsawLat = 52.229841
        val warsawLon = 21.011736
        val cities = meetup.listCities(warsawLat, warsawLon)

        // concatMapIterable로 항목이 하나 뿐인 Observable<Cities> 를 Observable<City>로 확장
        val cityObs = cities
            .concatMapIterable(Cities::results)

        val map = cityObs
            .filter { city ->
                city.distanceTo(warsawLat, warsawLon) < 50
            }
            .map(City::city)


        val geoNames = Retrofit.Builder()
            .baseUrl("https://api.geonames.org")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
            .create(GeoNames::class.java)

        val totalPopulation =
//            meetup
//            .listCities(warsawLat, warsawLon)
//            .concatMapIterable (Cities::results)
//            .filter{city->
//                city.distanceTo(warsawLat, warsawLon) < 50
//            }
//            .map(City::city)
            map
                .flatMap(geoNames::populationOf)
                .doOnNext {
                    Log.e("codb", it.toString())
                }
                .reduce(0L, { x, y -> x + y })
                .doOnError {
                    Log.e("codbs", "error")
                }


        testButton.setOnClickListener {
            meetup
                .listCities(52.229841, 21.011736)
                .concatMapIterable(extractCities())
                .map(toCityName())
                .toList()
                .subscribeOn(Schedulers.io()) // if not NetworkOnMainThreadException
                .observeOn(AndroidSchedulers.mainThread()) // backgroundThread에서 UI 갱신되어 CalledFromWrongThreadException 발생
                .subscribe(
                    putOnListView(),
                    displayError()
                )
        }
    }

    private fun listCities(lat: Double, lon: Double): Function<Void, Observable<Cities>> {
        return Function {
            meetup.listCities(lat, lon)
        }
    }

    private fun extractCities(): Function<Cities, Iterable<City>> {
        return Function {
            it.results
        }
    }

    private fun toCityName(): Function<City, String> {
        return Function {
            it.city
        }
    }

    private fun putOnListView(): Consumer<List<String>> {
        return Consumer<List<String>> {
            listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, it)
        }
    }

    private fun displayError(): Consumer<Throwable> {
        return Consumer {
            Log.e("cobds", "Error $it")
            Toast.makeText(this, "Unable to load cities", Toast.LENGTH_LONG).show()
        }
    }
}