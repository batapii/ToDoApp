package com.example.revengematch

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


// データベース内のテーブルを表すエンティティを定義。テーブル名は "word_table"
@Entity(tableName = "word_table")
data class Word(
    // "word"は各行を一意に識別する主キー。テーブル内のカラム名は "word"
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String,

    // "isChecked"は単語がチェックされているかを表す。テーブル内のカラム名は "is_checked"
    @ColumnInfo(name = "is_checked")
    var isChecked: Boolean = false
)
