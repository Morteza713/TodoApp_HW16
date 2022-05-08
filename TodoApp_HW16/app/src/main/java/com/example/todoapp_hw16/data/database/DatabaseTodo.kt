package com.example.todoapp_hw16.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp_hw16.data.model.User
import com.example.todoapp_hw16.data.model.Todo
import com.example.todoapp_hw16.data.model.dao.TodoDao
import com.example.todoapp_hw16.data.model.dao.UserDao
import com.example.todoapp_hw16.data.repository.ConverterObject

@Database(entities = [ User::class , Todo::class], version = 1, exportSchema = true)
@TypeConverters(ConverterObject::class)
abstract class DatabaseTodo:RoomDatabase() {

    abstract fun todoDao() : TodoDao
    abstract fun userDao() : UserDao

    companion object{
        private var INSTANCE:DatabaseTodo?=null
        fun getDatabase(context: Context) : DatabaseTodo{
            val temp = INSTANCE
            if (temp != null ){
                return temp
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    DatabaseTodo::class.java,
                    "task_manager"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}