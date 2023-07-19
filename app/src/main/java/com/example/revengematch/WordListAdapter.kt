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

class WordListAdapter(val onRemove: (Word) -> Unit, val onCheckBoxClicked: (Word) -> Unit)
    :ListAdapter<Word, WordListAdapter.WordViewHolder>(WordsComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.word, current.isChecked) { value ->
            val word = Word(current.word, value)
            onCheckBoxClicked.invoke(word)
        }
        holder.itemView.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                //.setTitle("${listPosition.word}")
                .setMessage("消す？")
                .setPositiveButton("Yes",DialogInterface.OnClickListener { _, _ ->
                    this@WordListAdapter.onRemove(getItem(position))
                })
                .setNegativeButton("No",null)
                .show()
        }
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        //private val todoList:ArrayList<Word>
        private val position:Int = adapterPosition
        //private val listPosition = todoList[position]
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)
        var cb : CheckBox = itemView.findViewById(R.id.checkBox)
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
            cb.setOnClickListener {
                //呼び出す
                onCheckBoxClicked.invoke(cb.isChecked)
            }
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.word == newItem.word
        }
    }
}

