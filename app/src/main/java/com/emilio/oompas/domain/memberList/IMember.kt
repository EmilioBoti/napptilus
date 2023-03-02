package com.emilio.oompas.domain.memberList

import com.emilio.oompas.models.Member

interface IMember {
    suspend fun getMembers(page: Int = 1)
    fun selectMember(pos: Int): Member?
}