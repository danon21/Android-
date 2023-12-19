package ru.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class СApiManager {
    private val BASE_URL = "http://127.0.0.1:8080/"   // Сервер с API

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: IApiService = retrofit.create(IApiService::class.java)

    /**
     * Метод возвращает таблицу рекордов
     */
    fun getRecordTableData(callback: Callback<List<CGame>>) {
        val call: Call<List<CGame>> = apiService.getGamesList()
        call.enqueue(object : Callback<List<CGame>> {
            // При наличие ответа от сервера
            override fun onResponse(call: Call<List<CGame>>, response: Response<List<CGame>>) {
                if (response.isSuccessful) {
                    val games: List<CGame>? = response.body()
                    callback.onResponse(call, Response.success(games))
                } else {
                    callback.onFailure(call, Throwable("Unsuccessful response"))
                }
            }
            // При проблеме с сервером
            override fun onFailure(call: Call<List<CGame>>, t: Throwable) {
                callback.onFailure(call, t)
            }
        })
    }
}