package com.emilio.oompas.service

import android.util.Log
import com.emilio.oompas.api.ApiEnPoint
import com.emilio.oompas.models.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class MemberServiceProvider(private val retrofitBuilder: Retrofit) : IRepository {

    override suspend fun retrieveCrewMembers(page: Int): ApiResponse? {
        val hashMap = HashMap<String, Int>().apply {
            this["page"] = page
        }
        return withContext(Dispatchers.IO) {
            try {
                val res = retrofitBuilder.create(ApiEnPoint::class.java).retrieveMembers(hashMap).execute()

                if (res.isSuccessful) {
                    res.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.e("ERROR", e.message?: "NetWork connection error")
                null
            }
        }
    }
}