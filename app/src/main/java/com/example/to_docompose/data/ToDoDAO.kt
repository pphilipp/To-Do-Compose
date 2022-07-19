package com.example.to_docompose.data

import androidx.room.*
import com.example.to_docompose.data.entity.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDAO {

    @Query("SELECT * FROM data_base_to_do ORDER BY id ASC")
    fun getAllTasks(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM data_base_to_do WHERE id=:taskId")
    fun getTaskById(taskId: Int) : Flow<ToDoTask>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTask(toDoTask: ToDoTask)

    @Update
    fun updateTask(toDoTask: ToDoTask)

    @Delete
    fun deleteTask(toDoTask: ToDoTask)

    @Query("DELETE FROM data_base_to_do")
    fun deleteAllTasks()

    @Query("SELECT * FROM data_base_to_do WHERE title=:searchQuery OR description=:searchQuery")
    fun search(searchQuery: String) : Flow<List<ToDoTask>>

    @Query("SELECT * FROM data_base_to_do ORDER BY " +
            "CASE WHEN priority LIKE 'L%' " +
            "THEN 1 WHEN priority LIKE 'M%' " +
            "THEN 2 WHEN priority LIKE 'H%' " +
            "THEN 3 END "
    )
    fun getSortedByLowPriority() : Flow<List<ToDoTask>>

    @Query("SELECT * FROM data_base_to_do ORDER BY " +
            "CASE WHEN priority LIKE 'H%' " +
            "THEN 1 WHEN priority LIKE 'M%' " +
            "THEN 2 WHEN priority LIKE 'L%' " +
            "THEN 3 END "
    )
    fun getSortedByHighPriority() : Flow<List<ToDoTask>>

}