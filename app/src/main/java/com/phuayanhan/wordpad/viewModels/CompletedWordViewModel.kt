package com.phuayanhan.wordpad.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.phuayanhan.wordpad.data.Model.Word
import com.phuayanhan.wordpad.repository.WordRepository
import com.phuayanhan.wordpad.repository.WordRepositoryFake
import kotlinx.coroutines.launch

class CompletedWordViewModel(val repo: WordRepository): ViewModel() {
    val words: MutableLiveData<List<Word>> = MutableLiveData()


    init {
        getWords("")
    }

    fun getWords(str:String) {
        viewModelScope.launch {
            val res = repo.getWords(str)
            words.value = res.filter { it.completed }
            Log.d("get words", words.value.toString() + "something")
        }
    }
    fun sortWords(order:String,by:String){
        viewModelScope.launch {
            val res=repo.sortWord(order,by)
            words.value=res.filter { !it.completed }
        }
    }
    class Provider(val repo: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CompletedWordViewModel(repo) as T
        }
    }
}