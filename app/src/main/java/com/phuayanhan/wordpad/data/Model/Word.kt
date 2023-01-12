package com.phuayanhan.wordpad.data.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
//data class of how the data will look like
data class Word(
    @PrimaryKey
    val id: Long? = null,
    val title: String,
    val meaning: String,
    val synonym: String,
    val details: String,
    var completed: Boolean = false,
    val date:Int
)