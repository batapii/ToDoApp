package com.example.revengematch

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication : Application() {

    // コルーチンスコープを作成。これはアプリケーションのライフサイクル全体にわたる。
    val applicationScope = CoroutineScope(SupervisorJob())

    // データベースのインスタンスを取得。必要になるまで初期化されない（遅延初期化）。
    val database by lazy { WordRoomDatabase.getDatabase(this,applicationScope) }

    // リポジトリのインスタンスを作成。必要になるまで初期化されない（遅延初期化）。
    val repository by lazy { WordRepository(database.wordDao()) }
}