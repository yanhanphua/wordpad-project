package com.phuayanhan.wordpad.repository

import android.util.Log
import com.phuayanhan.wordpad.data.Model.Word
import com.phuayanhan.wordpad.data.WordDao

class WordRepository(private val wordDao: WordDao) {

    // this is to call the function from wordDAO that gets all words
    suspend fun getWords(str: String): List<Word> {
        if(str == ""){
            return wordDao.getWords()
        }
        return wordDao.getWordsBySearch(str)
    }

    //this is to call a function from wordDAO to add a word
    suspend fun addWord(word: Word){
        wordDao.insert(word)
    }

    // this is to call a function from wordDAO to get a specific word by id
    suspend fun getWordById(id:Long):Word{
        return wordDao.getWordById(id.toInt())
    }

    // this is to call a function from wordDAO to update a specific word in the database
    suspend fun updateWord(id: Long,word: Word){
        return wordDao.insert(word.copy(id=id))
    }

    // this is to call a function from wordDAO to delete a word from the local database
    suspend fun deleteWord(id: Long){
        wordDao.delete(id.toInt())
    }

    //this is to call a function from wordDAO to change te current status of a specific word
    suspend fun completedWord(id:Long){
        wordDao.updateStatusById(id,true)
    }

    //this is to call a function from wordDAO to get all word and sort it according to what the user wants
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