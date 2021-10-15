package com.github.andrew1121andy.firebasematching.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.andrew1121andy.firebasematching.R
import com.github.andrew1121andy.firebasematching.databinding.RegisterLoginActivityBinding
import com.github.andrew1121andy.firebasematching.view.fragment.LoginFragment
import com.github.andrew1121andy.firebasematching.view.fragment.RegisterFragment
import com.github.andrew1121andy.firebasematching.viewmodel.RegisterLoginViewModel
import com.google.android.material.tabs.TabLayoutMediator

class RegisterLoginActivity: AppCompatActivity() {

    private lateinit var binding : RegisterLoginActivityBinding
    private val viewModel: RegisterLoginViewModel by viewModels()
    private val viewPagerAdapter = ViewPagerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.register_login_activity)
        binding.viewPager2.apply {
            adapter = viewPagerAdapter
            offscreenPageLimit = viewPagerAdapter.itemCount
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.setText(viewPagerAdapter.items[position].titleId)
        }.attach()
    }

    class ViewPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
        val items = listOf(
            Item(LoginFragment(), R.string.login_title),
            Item(RegisterFragment(), R.string.register_title)
        )

        override fun getItemCount(): Int = items.size

        override fun createFragment(position: Int): Fragment = items[position].fragment

        class Item(val fragment: Fragment, val titleId: Int)
    }

    companion object {
        fun start(activity: Activity) =
            activity.apply {
                finishAffinity()
                startActivity(Intent(this, RegisterLoginActivity::class.java))
            }
    }
}