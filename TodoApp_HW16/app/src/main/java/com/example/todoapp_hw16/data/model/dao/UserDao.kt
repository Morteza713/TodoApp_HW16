package com.example.todoapp_hw16.data.model.dao

import androidx.room.*
import com.example.todoapp_hw16.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table where userName == :username")
    fun getUsername(username:String): Flow<User?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user:User)

    @Delete
    suspend fun removeUser(user: User)

}