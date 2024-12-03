package com.bignerdranch.android.a4750.model

import android.graphics.Color

data class TaskItem(
    val title: String,
    val dueDate: String,
    val tag: String = "",
    val color: Int = Color.WHITE,
    val notes: String = "",
    val location: String = ""
)
