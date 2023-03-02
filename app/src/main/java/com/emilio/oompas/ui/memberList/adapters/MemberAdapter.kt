package com.emilio.oompas.ui.memberList.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emilio.oompas.R
import com.emilio.oompas.models.Member
import com.emilio.oompas.common.IOnClickItem
import com.emilio.oompas.ui.memberList.viewHolders.MemberViewHolder

class MemberAdapter(private val listMember: MutableList<Member>): RecyclerView.Adapter<MemberViewHolder>() {
    private var listener: IOnClickItem? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.member_item, parent, false)
        return MemberViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bindData(listMember[position])
    }

    override fun getItemCount(): Int = listMember.size

    fun setOnClickListener(listener: IOnClickItem?) {
        this.listener = listener
    }
}
