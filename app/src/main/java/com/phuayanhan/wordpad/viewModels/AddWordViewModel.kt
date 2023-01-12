package com.phuayanhan.wordpad.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.phuayanhan.wordpad.data.Model.Word
import com.phuayanhan.wordpad.repository.WordRepository
import com.phuayanhan.wordpad.repository.WordRepositoryFake
import kotlinx.coroutines.launch

class AddWordViewModel(private val repo: WordRepository) : ViewModel() {
    fun addWord(word: Word) {
        viewModelScope.launch{
            Log.d("get word", word.toString())
            repo.addWord(word)
        }
    }

    class Provider(private val repo: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddWordViewModel(repo) as T
        }
    }
}