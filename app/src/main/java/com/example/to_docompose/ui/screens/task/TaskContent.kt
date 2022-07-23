package com.example.to_docompose.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.data.entity.Priority
import com.example.to_docompose.ui.theme.LARGE_PADDING
import com.example.to_docompose.R
import com.example.to_docompose.ui.components.PriorityDropDown
import com.example.to_docompose.ui.theme.MEDIUM_PADDING
import com.example.to_docompose.ui.viewModel.SharedViewModel

@Composable
fun TaskContent(
    title: String,
    description: String,
    priority: Priority,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPrioritySelected: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(all = LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = {
                onTitleChange(it)
            },
            label = {
                Text(
                    text = stringResource(R.string.label_title)
                )
            },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
        )
        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colors.background
        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = { onPrioritySelected(it) }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = {
                Text(text = stringResource(id = R.string.label_description))
            },
            textStyle = MaterialTheme.typography.body1)
    }
}


@Preview
@Composable
fun TaskContentPreview() {
    TaskContent( "title preview","Description preview", Priority.NONE, {},{},{})
}