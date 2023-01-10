package com.phuayanhan.wordpad.Model

import java.util.Date

data class Word(
    val id: Long? = null,
    val title: String,
    val meaning: String,
    val synonym: String,
    val details: String,
    var completed: Boolean = false,
    val date:Int
)