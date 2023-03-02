package com.emilio.oompas.ui.memberDetail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emilio.oompas.R
import com.emilio.oompas.databinding.ActivityMemberDetailBinding
import com.emilio.oompas.models.Member
import com.squareup.picasso.Picasso

class MemberDetail : AppCompatActivity() {
    private lateinit var binding: ActivityMemberDetailBinding
    private var member: Member? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        member = intent.getParcelableExtra<Member>("data")

        setUpData()
        eventListener()
    }

    private fun eventListener() {
        binding.detailToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnSendEmail.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                data = Uri.parse("mailto:")
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, member?.email)
                startActivity(Intent.createChooser(this, "Choose"))
            }
        }

    }

    private fun setUpData() {
        member?.image?.let {
            Picasso.get()
                .load(it)
                .fit()
                .centerCrop()
                .into(binding.memberImage)
        }
        val name = member?.first_name
        val lastName = member?.last_name

        binding.memberName.text = "$name $lastName"
        binding.memberProfession.text = member?.profession
        binding.memberMail.text = member?.email
        binding.country.setSubTitleValue(member?.country ?: "")
        binding.age.setSubTitleValue(member?.age.toString() ?: "")
        binding.height.setSubTitleValue(member?.height.toString() ?: "")
        binding.gender.setSubTitleValue(member?.gender ?: "")
        binding.memberDescription.text = member?.description ?: getString(R.string.textAbout)
        binding.lyrics.text = member?.favorite?.song

    }

}