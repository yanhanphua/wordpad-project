package com.phuayanhan.wordpad

import android.app.Application
import com.phuayanhan.wordpad.repository.WordRepository

class MyApplication : Application() {
    val wordRepo = WordRepository.getInstance()
}