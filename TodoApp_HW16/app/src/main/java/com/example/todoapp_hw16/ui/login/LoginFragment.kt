package com.example.todoapp_hw16.ui.login

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.todoapp_hw16.MainActivity
import com.example.todoapp_hw16.R
import com.example.todoapp_hw16.data.model.User
import com.example.todoapp_hw16.databinding.LoginFragmentBinding

class LoginFragment:Fragment() {

    lateinit var binding: LoginFragmentBinding
    private val viewModel: UserViewModel by viewModels()
    lateinit var saveUser: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.login_fragment, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnSignIn.setOnClickListener {
            checkUserData(User(binding.etUsername.text.toString(), binding.etPassword.text.toString()))
        }
    }

    private fun checkUserData(user: User) {
        viewModel.getUsername(user.userName).observe(viewLifecycleOwner) {
            if (it != null) {
                if (it == user) {
                    val saveUserEditor = saveUser.edit()
                    saveUserEditor.putString("username", it.userName)
                    if (saveUserEditor.commit()) {
                        val intent = Intent(activity, MainActivity::class.java)
                        requireActivity().finish()
                        startActivity(intent)
                    }
                }
                else if (it.userName== user.userName && it.password != user.password) {
                    notifyForgetPassword()
                    }
                }
            }
        }
    private fun notifyForgetPassword() {
        Toast.makeText(requireContext(), "Forget Password", Toast.LENGTH_LONG).show()
    }
}