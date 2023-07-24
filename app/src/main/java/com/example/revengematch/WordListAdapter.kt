package com.example.revengematch

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

// リストアイテムを操作するためのアダプタークラス
class WordListAdapter(val onRemove: (Word) -> Unit, val onCheckBoxClicked: (Word) -> Unit)
    :ListAdapter<Word, WordListAdapter.WordViewHolder>(WordsComparator()) {

    // リストアイテムのビューホルダーを作成する
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    // ビューホルダーをデータにバインド（結びつけ）する
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.word, current.isChecked) { value ->
            val word = Word(current.word, value)
            onCheckBoxClicked.invoke(word)
        }

        // リストアイテムがクリックされたときの処理（ダイアログ表示）
        holder.itemView.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setMessage("消す？")
                .setPositiveButton("Yes",DialogInterface.OnClickListener { _, _ ->
                    this@WordListAdapter.onRemove(getItem(position))  // 削除処理を呼び出す
                })
                .setNegativeButton("No",null)
                .show()
        }
    }

    // リストアイテムを表示するためのビューホルダークラス
    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)
        var cb : CheckBox = itemView.findViewById(R.id.checkBox)

        // ビューホルダーにデータをバインドする
        fun bind(text: String?,isCheckBox:Boolean, onCheckBoxClicked: (Boolean) -> Unit) {
            wordItemView.text = text
            cb.isChecked = isCheckBox
            if (isCheckBox){
                wordItemView.paintFlags = wordItemView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG// 取り消し線
                wordItemView.setTextColor(Color.argb(25,0,0,0));    //カラー変更
            }else{
                wordItemView.paintFlags = wordItemView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv() //取り消し線の取り消し
                wordItemView.setTextColor(Color.argb(255,0,0,0));   //カラー変更
            }

            // チェックボックスがクリックされたときの処理を定義
            cb.setOnClickListener {
                //呼び出す
                onCheckBoxClicked.invoke(cb.isChecked)
            }
        }

        companion object {
            // ビューホルダーを作成するための静的メソッド
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    // リストアイテムの内容が同一であるかを判定するクラス
    class WordsComparator : DiffUtil.ItemCallback<Word>() {
        // リストアイテムが同一であるかを判定するメソッド
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }

        // リストアイテムの内容が同一であるかを判定するメソッド
        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.word == newItem.word
        }
    }
}

