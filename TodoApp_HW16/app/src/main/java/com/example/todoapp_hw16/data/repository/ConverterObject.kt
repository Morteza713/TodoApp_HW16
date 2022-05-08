package com.example.todoapp_hw16.data.repository

import androidx.room.TypeConverter
import com.example.todoapp_hw16.data.model.TodoState
import java.util.*

class ConverterObject {
    @TypeConverter
    fun fromState(todoState: TodoState):String{
        return todoState.name
    }
    @TypeConverter
    fun toState(valueState: String): TodoState {
        return TodoState.valueOf(valueState)
    }
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

}