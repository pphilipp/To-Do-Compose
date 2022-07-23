package com.example.to_docompose.ui.screens.task

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.to_docompose.data.entity.Priority
import com.example.to_docompose.data.entity.ToDoTask
import com.example.to_docompose.ui.viewModel.SharedViewModel
import com.example.to_docompose.util.Action

@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {

    val title: String by sharedViewModel.title
    val description: String by sharedViewModel.description
    val priority: Priority by sharedViewModel.priority

    val context: Context = LocalContext.current

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = { action ->
                    when (action) {
                        Action.NO_ACTION -> {
                            navigateToListScreen(action)
                        }
                        Action.ADD,
                        Action.UPDATE,
                        Action.DELETE,
                        Action.DELETE_ALL,
                        Action.UNDO -> {
                            if (sharedViewModel.validateFields()) {
                                navigateToListScreen(action)
                            } else {
                                displayToastMessage(context = context)
                            }
                        }
                    }

                }
            )
        },
        content = {
            TaskContent(
                title = title,
                description = description,
                priority = priority,
                onTitleChange = {
                    sharedViewModel.updateTitle(it)
                },
                onDescriptionChange = {
                    sharedViewModel.description.value = it
                },
                onPrioritySelected = {
                    sharedViewModel.priority.value = it
                }
            )
        })
}


fun displayToastMessage(context: Context) {
    Toast.makeText(context, " Fields should not be empty!", Toast.LENGTH_SHORT).show()
}
