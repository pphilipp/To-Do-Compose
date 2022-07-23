package com.example.to_docompose.ui.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_docompose.data.entity.Priority
import com.example.to_docompose.data.entity.ToDoTask
import com.example.to_docompose.repository.ToDoRepository
import com.example.to_docompose.util.Action
import com.example.to_docompose.util.Constants.EMPTY_STRING
import com.example.to_docompose.util.RequestState
import com.example.to_docompose.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Error
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
) : ViewModel() {

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf(EMPTY_STRING)

    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    private val _selectedTask = MutableStateFlow<ToDoTask?>(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                toDoRepository.getAllTasks().collect {
                    _allTasks.value = RequestState.Success(data = it)
                }
            }
        } catch (e: Throwable) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    fun handleDataBaseActions(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
            }
            Action.UPDATE -> {}
            Action.DELETE -> {}
            Action.DELETE_ALL -> {}
            Action.UNDO -> {}
            Action.NO_ACTION -> {}
            else -> {}
        }
        this.action.value = Action.NO_ACTION
    }

    fun addTask() = viewModelScope.launch(Dispatchers.IO) {
        val toDoTask = ToDoTask(
            title = title.value,
            description = description.value,
            priority = priority.value
        )
        toDoRepository.addTask(toDoTask)
    }

    fun getTaskById(taskId: Int) = viewModelScope.launch {
        toDoRepository.getTaskById(selectedTaskId = taskId).collect { task ->
            _selectedTask.value = task
        }
    }

    fun updateTaskFields(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < 20) {
            title.value = newTitle
        }
    }

    fun validateFields(): Boolean = title.value.isNotEmpty() && description.value.isNotEmpty()

}