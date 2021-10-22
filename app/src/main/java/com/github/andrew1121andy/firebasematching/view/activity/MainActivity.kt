package com.github.andrew1121andy.firebasematching.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.andrew1121andy.firebasematching.R
import android.app.DatePickerDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    companion object {
        fun start(activity: Activity) =
            activity.apply {
                finishAffinity()
                startActivity(Intent(this, MainActivity::class.java))
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener() {view, year, month, dayOfMonth->
                textView.text = "選択した日付は「${year}/${month + 1}/${dayOfMonth}」です"
            },
            2020,
            3,
            1)
        datePickerDialog.show()
    }
}
