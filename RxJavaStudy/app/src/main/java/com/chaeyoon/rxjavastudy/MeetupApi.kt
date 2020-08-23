package com.chaeyoon.rxjavastudy

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MeetupApi {
    @GET("/2/cities")
    fun listCities(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Observable<Cities>
}

data class Cities(
    val results: List<City> = listOf()
)
data class City(
    val city: String="",
    val country: String="",
    val distance: Double=0.0,
    val id: Int=0,
    val lat: Double=0.0,
    val localizedCountryName: String="",
    val lon: Double=0.0,
    val memberCount: Int=0,
    val ranking: Int=0,
    val zip: String=""
) {
    fun distanceTo(warsawLat: Double, warsawLon: Double):Double {
       return Math.sqrt(
           Math.pow((warsawLat - lat), 2.0) + Math.pow((warsawLon - lon), 2.0)
       )
    }
}