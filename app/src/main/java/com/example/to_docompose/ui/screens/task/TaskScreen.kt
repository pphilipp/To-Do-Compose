package com.example.to_docompose.ui.screens.task

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.example.to_docompose.data.entity.Priority
import com.example.to_docompose.data.entity.ToDoTask
import com.example.to_docompose.ui.viewModel.SharedViewModel
import com.example.to_docompose.util.Action

@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit,
) {
    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen
            )
        },
        content = {
            TaskContent(
                title = "TaskContentTitle",
                description = "description",
                priority = Priority.NONE,
                onTitleChange = {},
                onDescriptionChange = {},
                onPrioritySelected = {}
            )
        })
}