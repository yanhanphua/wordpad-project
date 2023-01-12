package com.phuayanhan.wordpad.repository

import android.util.Log
import com.phuayanhan.wordpad.data.Model.Word
import com.phuayanhan.wordpad.data.WordDao

class WordRepository(private val wordDao: WordDao) {
    suspend fun getWords():List<Word>{
        return wordDao.getWords()
    }
    suspend fun addWord(word: Word){
        wordDao.insert(word)
    }
    suspend fun getWordById(id:Long):Word{
        return wordDao.getWordById(id.toInt())
    }
    suspend fun updateWord(id: Long,word: Word){
        return wordDao.insert(word.copy(id=id))
    }
    suspend fun deleteWord(id: Long){
        wordDao.delete(id.toInt())
    }
    suspend fun completedWord(id:Long){
        wordDao.updateStatusById(id,true)
    }

}