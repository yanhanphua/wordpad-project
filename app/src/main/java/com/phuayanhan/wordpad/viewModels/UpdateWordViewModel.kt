package com.phuayanhan.wordpad.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.phuayanhan.wordpad.data.Model.Word
import com.phuayanhan.wordpad.repository.WordRepository
import com.phuayanhan.wordpad.repository.WordRepositoryFake
import kotlinx.coroutines.launch

class UpdateWordViewModel(private val repo: WordRepository) : ViewModel() {
    val word: MutableLiveData<Word> = MutableLiveData()

    fun getWordById(id: Long) {
        viewModelScope.launch{
            val res = repo.getWordById(id)
            res?.let {
                word.value = it
            }

        }
    }
    fun updateWord(id: Long, word: Word) {
        viewModelScope.launch {
            repo.updateWord(id, word)
        }
    }

    class Provider(val repo: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UpdateWordViewModel(repo) as T
        }
    }
}