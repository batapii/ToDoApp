package com.example.revengematch

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


// DAO (Data Access Object) interface
@Dao
interface WordDao {

    // データベースから全ての単語をアルファベット順で取得するクエリ
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>

    // 単語をデータベースに挿入するメソッド
    // 既存のエントリと衝突した場合は何もしない
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    // データベースから全ての単語を削除するメソッド
    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

    // 指定した単語をデータベースから削除するメソッド
    @Delete
    suspend fun delete(word: Word)

    // 指定した単語をデータベースで更新するメソッド
    @Update
    suspend fun update(word: Word)
}