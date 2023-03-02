package com.emilio.oompas.api

import com.emilio.oompas.models.ApiResponse
import com.emilio.oompas.models.Member
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiEnPoint {


    @GET("oompa-loompas")
    fun retrieveMembers(@QueryMap page: HashMap<String, Int>): Call<ApiResponse>

    @GET("oompa-loompas/{id}")
    fun retrieveOneMembers(@Path("id") id: Int): Call<Member>
}