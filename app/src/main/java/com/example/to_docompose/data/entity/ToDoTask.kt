package com.example.to_docompose.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.to_docompose.util.Constants.DATA_BASE_NAME

@Entity(tableName = DATA_BASE_NAME)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
)