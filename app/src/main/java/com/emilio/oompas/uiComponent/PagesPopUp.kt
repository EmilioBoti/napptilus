package com.emilio.oompas.uiComponent

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.RadioButton
import android.widget.RadioGroup
import com.emilio.oompas.R
import com.emilio.oompas.common.IOnClickItem

class PagesPopUp(context: Context, private val total: Int) {
    private var mDialog: Dialog
    private lateinit var optionContainer: RadioGroup
    private var listener: IOnClickItem? = null

    init {
        mDialog = Dialog(context).apply {
            setContentView(R.layout.option_pages_popup)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        optionContainer = mDialog.findViewById(R.id.optionPageContainer)
        optionContainer.removeAllViews()

        addRadioButton(context)
    }

    private fun addRadioButton(context: Context) {
        for (index : Int in 1..total) {
            val radioButton  = RadioButton(context).apply {
                this.id = index
                text = index.toString()
                setOnCheckedChangeListener { compoundButton, b ->
                    listener?.onClick(compoundButton, compoundButton.id)
                    mDialog.dismiss()
                }
            }
            optionContainer.addView(radioButton)
        }
    }

    fun setOnClick(listener: IOnClickItem) {
        this.listener = listener
    }

    fun showOptionPopUp() {
        mDialog.show()
    }

}