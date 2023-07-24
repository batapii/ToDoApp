package com.example.revengematch

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class NewWordActivity : AppCompatActivity(){ // AppCompatActivityを継承したNewWordActivityクラスを定義します。

    public override fun onCreate(savedInstanceStage: Bundle?){
        super.onCreate(savedInstanceStage)
        setContentView(R.layout.activity_new_word) // activity_new_wordレイアウトをこのアクティビティのビューとして設定します。

        val editWordView = findViewById<EditText>(R.id.edit_word) // UIからEditTextを取得します。

        val button = findViewById<Button>(R.id.button_save) // UIからButtonを取得します。
        button.setOnClickListener{ // ボタンにクリックリスナーをセットします。
            val replyIntent = Intent() // 新しいインテントを作成します。
            if (TextUtils.isEmpty(editWordView.text)){ // EditTextが空かどうかをチェックします。
                setResult(Activity.RESULT_CANCELED, replyIntent) // 結果としてRESULT_CANCELEDを設定し、このアクティビティを終了します。
            }else{
                val word = editWordView.text.toString() // EditTextのテキストを取得します。
                replyIntent.putExtra(EXTRA_REPLY,word) // インテントに追加データとして取得したテキストを追加します。
                setResult(Activity.RESULT_OK,replyIntent) // 結果としてRESULT_OKを設定し、このアクティビティを終了します。
            }
            finish() // アクティビティを終了します。
        }
    }
    companion object{
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY" // インテントでテキストを渡すためのキーを定義します。
    }
}