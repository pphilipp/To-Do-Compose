package com.example.to_docompose.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_docompose.ui.screens.list.ListScreen
import com.example.to_docompose.ui.screens.task.TaskScreen
import com.example.to_docompose.ui.viewModel.SharedViewModel
import com.example.to_docompose.util.Action
import com.example.to_docompose.util.Constants.LIST_ARGUMENT_KEY
import com.example.to_docompose.util.Constants.LIST_SCREEN
import com.example.to_docompose.util.Constants.TASK_ARGUMENT_KEY
import com.example.to_docompose.util.Constants.TASK_SCREEN
import com.example.to_docompose.util.toAction

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val argAction = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()

        LaunchedEffect(key1 = argAction) {
            sharedViewModel.action.value = argAction
        }

        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        sharedViewModel.getTaskById(taskId)
        val collectAsState by sharedViewModel.selectedTask.collectAsState()

        LaunchedEffect(key1 = collectAsState) {
            sharedViewModel.updateTaskFields(selectedTask = collectAsState)
        }

        TaskScreen(
            sharedViewModel = sharedViewModel,
            selectedTask = collectAsState,
            navigateToListScreen = navigateToListScreen
        )
    }
}