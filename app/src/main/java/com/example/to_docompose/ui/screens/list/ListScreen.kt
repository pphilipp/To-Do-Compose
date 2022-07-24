package com.example.to_docompose.ui.screens.list

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.R
import com.example.to_docompose.ui.viewModel.SharedViewModel
import com.example.to_docompose.util.Action
import com.example.to_docompose.util.SearchAppBarState
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }

    val action by sharedViewModel.action

    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDataBaseAction = { sharedViewModel.handleDataBaseActions(action = action) },
        onUndoClicked = {
            sharedViewModel.action.value = it
        },
        taskTitle = sharedViewModel.title.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = {
            ListContent(
                requestState = allTasks,
                navigateToTaskScreen = navigateToTaskScreen
            )
        },
        floatingActionButton = {
            ListFab(navigateToTaskScreen)
        }
    )
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.btn_add_description),
            tint = Color.White
        )
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDataBaseAction: () -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {
    handleDataBaseAction()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        when (action) {
            Action.NO_ACTION -> {}
            Action.ADD,
            Action.UPDATE,
            Action.DELETE,
            Action.DELETE_ALL,
            Action.UNDO -> {
                scope.launch {
                    val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                        message = "${action.name}: $taskTitle",
                        actionLabel = prepareActionLabel(action)
                    )

                    undoDeleteTask(
                        action = action,
                        snackBarResult = snackBarResult,
                        onUndoClicked = {
                            onUndoClicked(it)
                        }
                    )
                }
            }
        }
    }
}

private fun prepareActionLabel(action: Action): String = when (action) {
    Action.DELETE,
    Action.DELETE_ALL -> {
        "UNDO"
    }
    Action.ADD,
    Action.UPDATE,
    Action.UNDO,
    Action.NO_ACTION -> {
        "OK"
    }
}

private fun undoDeleteTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    when (snackBarResult) {
        SnackbarResult.Dismissed -> { /** do nothing  */ }
        SnackbarResult.ActionPerformed -> {
            if (action == Action.DELETE) {
                onUndoClicked(Action.UNDO)
            }
        }
    }
}

@Preview
@Composable
fun ListScreenPreview() {
//    ListScreen(navigateToTaskScreen = {})
}