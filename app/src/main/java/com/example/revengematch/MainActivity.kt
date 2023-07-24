import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.revengematch.Word
import com.example.revengematch.WordDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 1) // このアノテーションは、WordクラスをSQLiteデータベースのスキーマとしてRoomに指示します
abstract class WordRoomDatabase : RoomDatabase() { // RoomDatabaseのサブクラスとして抽象クラスを作成します

    abstract fun wordDao(): WordDao // DAOを取得する抽象メソッドを定義します。Roomはこのコードを実装します。

    companion object { // コンパニオンオブジェクト。シングルトンとしてデータベースインスタンスを提供します。

        @Volatile
        private var INSTANCE: WordRoomDatabase? = null // INSTANCE変数を保持します。これはデータベースへの参照を保持します。

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase {
            // INSTANCEがnullでなければそれを返し、nullであればデータベースを作成します
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                )
                    .fallbackToDestructiveMigration() // Migrationオブジェクトがなければ、データベースを破壊して再構築します。
                    .addCallback(WordDatabaseCallback(scope)) // データベースが作成されたときに呼び出すコールバックを追加します。
                    .build() // データベースをビルドします。

                INSTANCE = instance // インスタンスをINSTANCE変数に格納します。
                instance // インスタンスを返します。
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            // データベースの作成時にデータベースをポピュレートします。
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) { // 別のスレッドでデータベースをポピュレートします。
                        populateDatabase(database.wordDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao) {
            // アプリをクリーンなデータベースで開始します。作成時にのみポピュレートする場合は不要です。
            wordDao.deleteAll() // すべてのワードを削除します。

            var word = Word("テスト") // ワードを作成します。
            wordDao.insert(word) // データベースにワードを挿入します。
            word = Word("テスト") // 新しいワードを作成します。
            wordDao.insert(word) // データベースに新しいワードを挿入します。
        }
    }
}
