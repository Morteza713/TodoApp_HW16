package com.example.todoapp_hw16.data.model

import androidx.room.Embedded
import androidx.room.Relation


data class UserTodo(
    @Embedded val user: User,
    @Relation(parentColumn = "userName", entityColumn = "userCreator")
    val todo: List<Todo>
)