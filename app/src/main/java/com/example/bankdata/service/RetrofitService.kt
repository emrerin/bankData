package com.example.bankdata.service

import com.example.bankdata.model.BDataModel
import com.example.bankdata.utils.Constants.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {
    // base_url : https://raw.githubusercontent.com/
    // ->   : fatiha380/mockjson/main/bankdata

    @GET("fatiha380/mockjson/main/bankdata")
    fun getData(): Call<List<BDataModel>>

    companion object {

        var retrofitService: RetrofitService? = null

        //Create the Retrofit service instance using the retrofit.
        fun getInstance(): RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}