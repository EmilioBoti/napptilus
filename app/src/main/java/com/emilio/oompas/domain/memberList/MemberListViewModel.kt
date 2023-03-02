package com.emilio.oompas.domain.memberList


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emilio.oompas.models.Member
import com.emilio.oompas.service.IRepository


class MemberListViewModel(private val repository: IRepository): ViewModel(), IMember {
    var currentPage: Int = 1
    var total: Int = 0
    private var _listMembers: MutableList<Member> = mutableListOf()
    var listMembers: MutableLiveData<MutableList<Member>> = MutableLiveData()
    private val filterList: HashMap<String, String> = HashMap<String, String>()



    override suspend fun getMembers(page: Int) {
        currentPage = page
        val list = repository.retrieveCrewMembers(currentPage)
        list?.let { memberLis ->
            total = memberLis.total
            _listMembers = memberLis.results
            listMembers.postValue(_listMembers)
        }
    }

    override fun selectMember(pos: Int): Member? {
        return if (isPositionValid(pos)) {
            _listMembers[pos]
        } else {
            null
        }
    }

    fun searchMember(value: String) {
        if (value.isNotEmpty()) {
            listMembers.postValue(memberFound(value))
        } else {
            listMembers.postValue(_listMembers)
        }
    }

    fun getGender(): List<String> {
        val genderList = mutableListOf<String>()
        _listMembers.forEach {
            if (!genderList.contains(it.gender)) {
                it.gender?.let { gen ->
                    genderList.add(gen)
                }
            }
        }
        return genderList
    }

    fun getProfession(): List<String> {
        val professionList = mutableListOf<String>()
        _listMembers.forEach {
            if (!professionList.contains(it.profession)) {
                it.profession?.let { gen ->
                    professionList.add(gen)
                }
            }
        }
        return professionList
    }

    fun addFilter(value: String, typeFilter: TypeFilter) {
        filterList[typeFilter.type] = value
    }

    fun removeFilter(typeFilter: TypeFilter) {
        filterList.remove(typeFilter.type)
    }

    fun filterMember() {
        listMembers.postValue(applyFilterToMembers())
    }

    private fun applyFilterToMembers(): MutableList<Member> {

        return if (filterList.keys.size >= 2) {
            _listMembers.filter {
                it.gender == filterList[TypeFilter.GENDER.type] && it.profession == filterList[TypeFilter.PROFESSION.type]
            } as MutableList<Member>
        } else if (filterList.keys.size <= 1) {
            _listMembers.filter {
                it.gender == filterList[TypeFilter.GENDER.type] || it.profession == filterList[TypeFilter.PROFESSION.type]
            } as MutableList<Member>
        } else {
            _listMembers
        }
    }

    private fun memberFound(value: String): MutableList<Member> {
        val valueLowerCase = value.lowercase()
        return _listMembers.filter {
            it.first_name?.lowercase()?.contains(valueLowerCase) == true ||
            it.last_name?.lowercase()?.contains(valueLowerCase) == true ||
            it.profession?.lowercase()?.contains(valueLowerCase) == true
        } as MutableList<Member>
    }

    fun isPositionValid(pos: Int): Boolean = pos <= _listMembers.size


}
