package com.github.andrew1121andy.firebasematching.view.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.andrew1121andy.firebasematching.R
import com.github.andrew1121andy.firebasematching.databinding.PersonalInformationActivityBinding
import com.github.andrew1121andy.firebasematching.viewmodel.PersonalInformationViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class PersonalInformationActivity: AppCompatActivity() {

    private lateinit var binding : PersonalInformationActivityBinding
    private val viewModel: PersonalInformationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.personal_information_activity)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.birthdayText.setOnClickListener {
            showBirthdaySelectDialog()
        }
        viewModel.apply {
            registerError.observe(this@PersonalInformationActivity) {
                binding?.root?.also {
                    Snackbar.make(it, R.string.register_personal_info_error, Snackbar.LENGTH_SHORT).show()
                }
            }
            registerSuccess.observe(this@PersonalInformationActivity) {
               // TODO:次のActivityへ進む
            }
        }
    }

    private fun showBirthdaySelectDialog() {
        val initTime =
            if (viewModel.birthday != 0L)
                viewModel.birthday
            else
                Calendar.getInstance().apply {
                    add(Calendar.YEAR, -25)
                }.timeInMillis
        val calendar = Calendar.getInstance().apply {
            timeInMillis = initTime
        }
        DatePickerDialog(this, { _, year, month, dayOfMonth ->
                viewModel.updateBirthday(
                    Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }.timeInMillis
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH))
            .show()
    }

    companion object {
        fun start(activity: Activity) {
            activity.apply {
                finishAffinity()
                startActivity(Intent(activity, PersonalInformationActivity::class.java))
            }
        }
    }
}