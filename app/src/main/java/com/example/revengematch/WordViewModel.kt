package com.example.revengematch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WordViewModel (private val repository: WordRepository) : ViewModel(){
    // リポジトリから全ての単語を取得し、それをLiveDataに変換します。
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    // 新しい単語をデータベースに挿入するためのメソッドです。
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }

    // データベースから単語を削除するためのメソッドです。
    fun delete(word: Word) = viewModelScope.launch {
        repository.delete(word)
    }

    // 単語のチェックステータスを更新するためのメソッドです。
    fun updateCheckStatus(word: Word) = viewModelScope.launch {
        repository.updateCheckStatus(word)
    }
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory{
    // ViewModelのインスタンスを生成します。
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // ViewModelがWordViewModelのインスタンスである場合、リポジトリを引数にして新しいWordViewModelを生成します。
        if (modelClass.isAssignableFrom(WordViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        // もしWordViewModel以外のViewModelが要求された場合、例外をスローします。
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
