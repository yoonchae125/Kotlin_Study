package com.chaeyoon.rxjavastudy

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoNames {
    fun populationOf(query:String): Observable<Int> {
        return search(query)
            .concatMapIterable (SearchResult::geoNames)
            .map(GeoName::population)
            .filter{
                it != null
            }
//            .single(0)
            .doOnError {t->
                Log.w("codbs", "Falling back to 0 for {$query $t}")
            }
            .onErrorReturn { 0 }
            .subscribeOn(Schedulers.io())
//            .toObservable()
    }
    fun search(query: String):Observable<SearchResult>{
        return search(query, 1, "LONG","some_user")
    }
    @GET("/searchJSON")
    fun search(
        @Query("q") query: String,
        @Query("maxRows") maxRows: Int,
        @Query("style") style: String,
        @Query("username") username: String
    ): Observable<SearchResult>
}

data class SearchResult(
    val geoNames: List<GeoName> = ArrayList()
)

data class GeoName(
    val lat: String="",
    val lng: String="",
    val geonameId: Int=0,
    val population: Int=0,
    val countryCode: String="",
    val name: String=""
)
