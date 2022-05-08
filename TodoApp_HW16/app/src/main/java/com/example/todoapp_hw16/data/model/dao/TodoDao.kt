package com.example.todoapp_hw16.data.model.dao

import androidx.room.*
import com.example.todoapp_hw16.data.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTodo(todo: Todo)

    @Update
    suspend fun editTodo(todo: Todo)

    @Delete
    suspend fun removeTodo(todo: Todo)

    @Query("DELETE FROM todo_table")
    suspend fun removeAll()

    @Query("SELECT * FROM todo_table WHERE userCreator = :id")
    fun getTodoUserById(id:String): Flow<List<Todo>>

    @Query("SELECT * FROM todo_table")
    fun getAllTodo():Flow<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE title LIKE :keyWord")
    fun searchTodo(keyWord:String):Flow<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE id = :id")
    fun searchById(id: Int):Flow<Todo>
}