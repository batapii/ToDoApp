package com.example.revengematch

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class WordRepository (private val wordDao: WordDao){
    val allWords : Flow<List<Word>> = wordDao.getAlphabetizedWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word){
        wordDao.insert(word)
    }
    suspend fun delete(word: Word) {
        wordDao.delete(word)
    }
    suspend fun updateCheckStatus(word: Word) {
        wordDao.update(word)
    }
}