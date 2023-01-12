package com.phuayanhan.wordpad.repository

import android.util.Log
import com.phuayanhan.wordpad.data.Model.Word
import com.phuayanhan.wordpad.data.WordDao

class WordRepository(private val wordDao: WordDao) {
    suspend fun getWords(str: String): List<Word> {
        if(str == ""){
            return wordDao.getWords()
        }
        return wordDao.getWordsBySearch(str)
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
    suspend fun sortWord(order:String,by:String):List<Word>{
        if(order=="Ascending" && by=="Title") {
            val res = wordDao.getWords()
            return res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.title })
        }else if(order=="Descending" && by=="Title") {
            val res = wordDao.getWords()
            return res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.title }).reversed()
        }else if(order=="Descending" && by=="Date") {
            return wordDao.getWords().reversed()
        }
        return wordDao.getWords()
    }
}