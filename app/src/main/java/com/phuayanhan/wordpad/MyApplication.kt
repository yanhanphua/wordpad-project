package com.phuayanhan.wordpad

import android.app.Application
import androidx.room.Room
import com.phuayanhan.wordpad.data.WordpadDatabase
import com.phuayanhan.wordpad.repository.WordRepository
import com.phuayanhan.wordpad.repository.WordRepositoryFake

class MyApplication : Application() {
    val wordRepoFake = WordRepositoryFake.getInstance()

    // this is to declare word repository and connect it to the database
    lateinit var wordRepo:WordRepository
    override fun onCreate() {
        super.onCreate()
        val wordpadDatabase = Room.databaseBuilder(this,
            WordpadDatabase::class.java,
            WordpadDatabase.DATABASE_NAME).fallbackToDestructiveMigration().build()
        wordRepo= WordRepository(wordpadDatabase.wordDao)
    }
}