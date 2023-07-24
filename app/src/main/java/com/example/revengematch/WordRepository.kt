package com.example.revengematch

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

// リポジトリクラス
// これはDAOとUIの間の抽象化レイヤであり、UIがデータベース操作について知る必要がなくなる
class WordRepository (private val wordDao: WordDao){

    // 単語のリストをフローとして保持
    val allWords : Flow<List<Word>> = wordDao.getAlphabetizedWords()

    // ワーカースレッド上で実行されるべき非同期関数
    // ここでは新しい単語をデータベースに追加
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word){
        wordDao.insert(word)
    }
    // データベースから指定された単語を削除する関数
    suspend fun delete(word: Word) {
        wordDao.delete(word)
    }
    // データベース上の指定された単語のチェック状態を更新する関数
    suspend fun updateCheckStatus(word: Word) {
        wordDao.update(word)
    }
}