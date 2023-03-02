package com.emilio.oompas.service

import com.emilio.oompas.models.ApiResponse
import com.emilio.oompas.models.Member

interface IRepository {
    suspend fun retrieveCrewMembers(page: Int): ApiResponse?
}