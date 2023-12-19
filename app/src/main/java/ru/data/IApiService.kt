package ru.data

import retrofit2.Call
import retrofit2.http.GET

interface IApiService {
    @GET("games")
    fun getGamesList(): Call<List<CGame>>
}