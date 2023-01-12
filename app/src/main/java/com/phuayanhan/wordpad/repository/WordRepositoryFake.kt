package com.phuayanhan.wordpad.repository

import android.util.Log
import com.phuayanhan.wordpad.data.Model.Word

class WordRepositoryFake {
    private val wordsMap: MutableMap<Long, Word> = mutableMapOf(
        0L to Word(
            0L,
            "Metanoia",
            "The journey of changing one's word, heart or a way ot life.",
            "something",
            "something",
            false,
            2

        ), 1L to Word(
            1L,
            "aMetanoi",
            "The journey of changing one's word, heart or a way ot life.",
            "something",
            "something",
            true,
            1
        )
    )
    private var counter = wordsMap.size.toLong()

    fun getWords(str:String): List<Word> {
        return wordsMap.filter { (key, value) -> Regex(str,RegexOption.IGNORE_CASE).containsMatchIn(value.title)}.values.toList()
    }
//    fun sortByTitle():List<Word>{
//        Log.d("idkwtfimdoing",wordsMap.toSortedMap().toString())
//    }
    fun addWord(word: Word): Word? {
        wordsMap[++counter] = word.copy(id = counter)
        return wordsMap[counter]
    }

    fun getWordById(id: Long): Word? {
        return wordsMap[id]
    }
    fun sortWord(order:String,by:String):List<Word>{
        if(order=="Ascending" && by=="Title") {
            val res = wordsMap.values.toList()
            return res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.title })
        }else if(order=="Descending" && by=="Title") {
            val res = wordsMap.values.toList()
            return res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.title }).reversed()
        }else if(order=="Descending" && by=="Date"){
            return wordsMap.values.toList().reversed()
        }
        return wordsMap.values.toList()
    }

    fun updateWord(id: Long, note: Word): Word? {
        wordsMap[id] = note
        return wordsMap[id]
    }

    fun deleteWord(id: Long) {
        wordsMap.remove(id)
    }
    fun completedWord(id:Long): Word?{

        wordsMap[id]?.completed=true
        Log.d("halp",wordsMap[id].toString())
        return wordsMap[id]
    }

    companion object {
        var wordRepositoryFake: WordRepositoryFake? = null

        fun getInstance(): WordRepositoryFake {
            if (wordRepositoryFake == null) {
                wordRepositoryFake = WordRepositoryFake()
            }

            return wordRepositoryFake!!
        }
    }
}