package com.emilio.oompas.ui.memberList.viewHolders

import android.net.Uri
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emilio.oompas.R
import com.emilio.oompas.models.Member
import com.emilio.oompas.common.IOnClickItem
import com.squareup.picasso.Picasso

class MemberViewHolder(itemView: View, private val listener: IOnClickItem?): ViewHolder(itemView) {
    private val name: AppCompatTextView? = itemView.findViewById(R.id.memberName)
    private val profession: AppCompatTextView? = itemView.findViewById(R.id.memberProfession)
    private val image: AppCompatImageView? = itemView.findViewById(R.id.memberImage)
    private val gender: AppCompatTextView? = itemView.findViewById(R.id.memberGender)

    fun bindData(member: Member) {
        name?.text = member.first_name
        profession?.text = member.profession
        gender?.text = member.gender

        val imageUri: Uri = Uri.parse(member.image)

        image?.let {
            Picasso.get()
                .load(imageUri)
                .fit()
                .centerCrop()
                .into(image)
        }

        itemView.setOnClickListener {
            listener?.onClick(it, absoluteAdapterPosition)
        }
    }
}