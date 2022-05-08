package com.example.todoapp_hw16.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp_hw16.data.database.DatabaseTodo
import com.example.todoapp_hw16.data.model.User
import com.example.todoapp_hw16.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application):AndroidViewModel(application) {

    private val userDao = DatabaseTodo.getDatabase(application).userDao()

    private val userRepository: UserRepository = UserRepository(userDao)

    fun getUsername(username: String): LiveData<User?> {
        return userRepository.getUsername(username).asLiveData()
    }
    fun addUser(user: User) {
        viewModelScope.launch() {
            userRepository.addUser(user)
        }
    }
    fun removeUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.removeUser(user)
        }
    }
}