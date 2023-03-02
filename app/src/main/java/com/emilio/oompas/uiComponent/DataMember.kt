package com.emilio.oompas.uiComponent

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import com.emilio.oompas.R
import com.emilio.oompas.databinding.DataLayoutBinding

class DataMember(context: Context, attrs: AttributeSet?): LinearLayoutCompat(context, attrs) {
    private lateinit var binding: DataLayoutBinding
    private lateinit var inflater: LayoutInflater
    private var attributes: TypedArray

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataLayoutBinding.inflate(inflater,this, true)

        attributes = context.obtainStyledAttributes(attrs, R.styleable.DataMember)
        attributes.getString(R.styleable.DataMember_data)?.let { setSubTitle(it) }
    }

    fun setSubTitle(text: String) {
        binding.subTitle.text = text
    }

    fun setSubTitleValue(text: String) {
        binding.value.text = text
    }
}