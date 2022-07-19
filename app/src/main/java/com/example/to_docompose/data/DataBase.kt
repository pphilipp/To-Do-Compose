package com.example.to_docompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.to_docompose.data.entity.ToDoTask

@Database(
    entities = [ToDoTask::class],
    version = 1,
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDAO

}