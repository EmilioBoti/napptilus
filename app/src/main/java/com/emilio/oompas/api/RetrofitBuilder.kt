package com.emilio.oompas.api

import com.emilio.oompas.util.Const
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    companion object {

        fun getInstances(): Retrofit {
            return  Retrofit.Builder().baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}