package com.example.to_docompose.data.entity


import androidx.compose.ui.graphics.Color
import com.example.to_docompose.ui.theme.PriorityHigh
import com.example.to_docompose.ui.theme.PriorityLow
import com.example.to_docompose.ui.theme.PriorityMedium
import com.example.to_docompose.ui.theme.PriorityNone

enum class Priority(val color: Color) {
    HIGH(PriorityHigh),
    MEDIUM(PriorityMedium),
    LOW(PriorityLow),
    NONE(PriorityNone)
}