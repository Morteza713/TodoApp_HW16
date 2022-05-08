package com.example.todoapp_hw16.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user_table")
data class User(@PrimaryKey val userName:String, var password:String )