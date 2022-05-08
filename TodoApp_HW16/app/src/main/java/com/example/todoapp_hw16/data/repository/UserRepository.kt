package com.example.todoapp_hw16.data.repository

import com.example.todoapp_hw16.data.model.User
import com.example.todoapp_hw16.data.model.dao.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    fun getUsername(userName: String) : Flow<User?> {
        return userDao.getUsername(userName)
    }
    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
    suspend fun removeUser(user: User){
        userDao.removeUser(user)
    }

}