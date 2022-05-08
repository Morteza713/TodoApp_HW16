package com.example.todoapp_hw16.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.todoapp_hw16.R
import com.example.todoapp_hw16.databinding.LoginActivityBinding
import com.google.android.material.tabs.TabLayoutMediator

class LoginActivity() : AppCompatActivity() {

    lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity)
        setContentView(binding.root)

        binding.vpLogin.adapter = PagerAdapterLogin(supportFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabLogin,binding.vpLogin) { tab, position ->
            when (position) {
                0 -> tab.text = "Login"
                1 -> tab.text = "SignIn"
            }
        }.attach()

    }
}