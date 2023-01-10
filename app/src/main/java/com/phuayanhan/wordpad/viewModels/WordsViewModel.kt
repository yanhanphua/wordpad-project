package com.phuayanhan.wordpad.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.phuayanhan.wordpad.Model.Word
import com.phuayanhan.wordpad.repository.WordRepository

class WordsViewModel(val repo: WordRepository): ViewModel() {
    val words: MutableLiveData<List<Word>> = MutableLiveData()


    init {
        getWords("")
    }

    fun getWords(str:String) {
        val res = repo.getWords(str)
        val ans2=res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER){ it.title })
        val ans=res.reversed()
        Log.d("reverseDate",ans2.toString())
        words.value = res.filter { !it.completed }

        Log.d("get words", words.value.toString() + "something")
    }
    fun sortWords(order:String,by:String){
        val res=repo.sortWord(order,by)
        words.value=res.filter { !it.completed }
    }

    class Provider(val repo: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return WordsViewModel(repo) as T
        }
    }
}