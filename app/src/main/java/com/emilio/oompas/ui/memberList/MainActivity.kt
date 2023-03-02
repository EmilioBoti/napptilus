package com.emilio.oompas.ui.memberList

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.emilio.oompas.R
import com.emilio.oompas.api.RetrofitBuilder
import com.emilio.oompas.databinding.ActivityMainBinding
import com.emilio.oompas.domain.memberList.MemberListViewModel
import com.emilio.oompas.models.Member
import com.emilio.oompas.service.IRepository
import com.emilio.oompas.service.MemberServiceProvider
import com.emilio.oompas.common.IOnClickItem
import com.emilio.oompas.domain.memberList.TypeFilter
import com.emilio.oompas.ui.memberDetail.MemberDetail
import com.emilio.oompas.ui.memberList.adapters.MemberAdapter
import com.emilio.oompas.uiComponent.PagesPopUp
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var repository: IRepository
    private lateinit var viewModel: MemberListViewModel
    private lateinit var mDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        repository = MemberServiceProvider(RetrofitBuilder.getInstances())
        viewModel = MemberListViewModel(repository)


        mDialog = Dialog(this)
        binding.btnPages.text = "Page #${viewModel.currentPage}"

        binding.btnPages.setOnClickListener {
            PagesPopUp(this, viewModel.total).apply {
                showOptionPopUp()
                setOnClick(object : IOnClickItem {
                    override fun onClick(view: View, pos: Int) {
                        lifecycleScope.launchWhenStarted {
                            viewModel.getMembers(pos)
                        }
                        binding.btnPages.text = "Page #${viewModel.currentPage}"
                        Toast.makeText(this@MainActivity, "${pos}", Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }

        viewModel.listMembers.observe(this) { list ->
            setAdapter(list)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getMembers()
        }

        eventListener()
    }

    private fun showFilterPopUp() {
        mDialog.setContentView(R.layout.filter_popup)

        val genderFilter = mDialog.findViewById<ChipGroup>(R.id.genderFilter)
        val professionFilter = mDialog.findViewById<ChipGroup>(R.id.proFilterContainer)
        val btnApply = mDialog.findViewById<AppCompatTextView>(R.id.btnApply)

        btnApply.setOnClickListener {
            viewModel.filterMember()
            mDialog.dismiss()
        }

        viewModel.getGender().forEach { gen ->
            genderFilter.addView(createChip(gen, TypeFilter.GENDER))
        }

        viewModel.getProfession().forEach { pro ->
            professionFilter.addView(createChip(pro, TypeFilter.PROFESSION))
        }

        mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.show()
    }

    private fun createChip(text: String,  typeFilter: TypeFilter) : Chip {

        return Chip(this).apply {
            this.text = text
            this.isCheckable = true
            this.chipStrokeWidth = 3f
            this.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.green_dark)))
            this.setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    viewModel.addFilter(compoundButton.text.toString().trim(), typeFilter)
                }else {
                    viewModel.removeFilter(typeFilter)
                }
            }
        }
    }

    private fun eventListener() {

        val searchInput = binding.toolbar.menu.findItem(R.id.btnMenuSearch).actionView as SearchView

        searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                lookFor(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lookFor(newText)
                return true
            }

        })

        binding.btnFilter.setOnClickListener {
            showFilterPopUp()
        }
    }

    private fun lookFor(text: String?) {
        text?.let { value -> viewModel.searchMember(value) }
    }

    private fun setAdapter(list: MutableList<Member>) {

        val adapter = MemberAdapter(list)
        adapter.setOnClickListener(object : IOnClickItem {

            override fun onClick(view: View, pos: Int) {
                viewModel.selectMember(pos)?.let { member ->
                    navigateToDetail(member)
                }
            }

        })

        binding.membersContainer.apply {
            this.layoutManager = GridLayoutManager(this@MainActivity, 2)
            this.adapter = adapter
        }
    }

    private fun navigateToDetail(member: Member) {
        Intent(this@MainActivity, MemberDetail::class.java).apply {
            this.putExtra("data", member)
            startActivity(this)
        }
    }
}