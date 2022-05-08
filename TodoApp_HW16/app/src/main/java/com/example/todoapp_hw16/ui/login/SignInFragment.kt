package com.example.todoapp_hw16.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.todoapp_hw16.R
import com.example.todoapp_hw16.data.model.User
import com.example.todoapp_hw16.databinding.SigninFragmentBinding

class SignInFragment():Fragment(R.layout.signin_fragment) {

    lateinit var binding: SigninFragmentBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SigninFragmentBinding.bind(view)

        binding.btnSignIn.setOnClickListener {
            checkUserData( User(binding.etUsername.text.toString(), binding.etPassword.text.toString()))
        }
    }
    private fun checkUserData(user: User) {
         viewModel.addUser(user)
            notifySign()
    }

    private fun notifySign() {
        Toast.makeText(requireContext(), "user Signed", Toast.LENGTH_LONG).show()
    }

}
