package com.emilio.oompas.domain.memberList

import com.emilio.oompas.api.RetrofitBuilder
import com.emilio.oompas.models.Member
import com.emilio.oompas.service.MemberServiceProvider
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class MemberListViewModelTest {

    @RelaxedMockK
    private lateinit var repository: MemberServiceProvider
    private lateinit var viewModel: MemberListViewModel

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        repository = MemberServiceProvider(RetrofitBuilder.getInstances())
        viewModel = MemberListViewModel(repository)

        runBlocking {
            viewModel.getMembers(1)
        }
    }

    @Test
    fun `check if member selected `() {
        assertTrue(viewModel.isPositionValid(1))
    }
}