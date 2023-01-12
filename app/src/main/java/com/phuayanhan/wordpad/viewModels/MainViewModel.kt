package com.phuayanhan.wordpad.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.phuayanhan.wordpad.repository.WordRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    val refreshWords:MutableSharedFlow<Pair<String,String>> = MutableSharedFlow()
    val refreshCompletedWords:MutableSharedFlow<Pair<String,String>> = MutableSharedFlow()

    fun onRefreshWords(order:String,by:String){
        viewModelScope.launch {
            refreshWords.emit(Pair(order,by))
        }
    }
    fun onRefreshCompletedWords(order:String,by:String){
        viewModelScope.launch {
            refreshCompletedWords.emit(Pair(order,by))
        }
    }
}