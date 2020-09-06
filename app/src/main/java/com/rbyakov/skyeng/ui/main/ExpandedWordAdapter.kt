package com.rbyakov.skyeng.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rbyakov.skyeng.R
import com.rbyakov.skyeng.network.models.Word

class ExpandedWordAdapter(private val items: List<Word>, private val onClick: (Word) -> Unit) :
    RecyclerView.Adapter<WordAdapter.WordHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordAdapter.WordHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_item, parent, false)
        return WordAdapter.WordHolder(inflatedView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: WordAdapter.WordHolder, position: Int) {
        holder.bindPayment(items[position], onClick)
    }
}