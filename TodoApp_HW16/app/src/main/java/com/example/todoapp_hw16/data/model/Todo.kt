package com.example.todoapp_hw16.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity("todo_table")
data class Todo(@PrimaryKey(autoGenerate = true) val id:Int, val title:String,
                val description : String, val state: TodoState, val userCreator:String,
                val dateTime : Date, val picUri : String? = null
)