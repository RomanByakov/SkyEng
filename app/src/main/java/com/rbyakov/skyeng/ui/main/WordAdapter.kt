package com.rbyakov.skyeng.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rbyakov.skyeng.R
import com.rbyakov.skyeng.network.models.Word
import kotlinx.android.synthetic.main.expanded_word_item.view.*
import kotlinx.android.synthetic.main.word_item.view.count
import kotlinx.android.synthetic.main.word_item.view.meaning
import kotlinx.android.synthetic.main.word_item.view.title

class WordAdapter(
    private val onClick: (Word) -> Unit,
    private val onExpand: (Word, Int) -> Unit
) :
    PagedListAdapter<Word, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MEANING_ITEM -> {
                val inflatedView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.word_item, parent, false)
                WordHolder(inflatedView)
            }
            else -> {
                val inflatedView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.expanded_word_item, parent, false)
                ExpandableWordHolder(
                    inflatedView
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        item?.let {
            if (it.meanings.size > 1) {
                return WORD_ITEM
            }
        }
        return MEANING_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { word ->
            if (holder is ExpandableWordHolder) {
                holder.bindPayment(word, onExpand, onClick)
            } else {
                (holder as WordHolder).bindPayment(word, onClick)
            }
        }
    }

    class WordHolder(v: View) :
        RecyclerView.ViewHolder(v) {

        private var view: View = v

        fun bindPayment(item: Word?, onClick: (Word) -> Unit) {
            item?.let {
                view.setOnClickListener { onClick.invoke(item) }
                view.title.text = item.text
                view.count.isVisible = item.meanings.size > 1
                view.count.text = itemView.resources.getQuantityString(
                    R.plurals.meanings,
                    item.meanings.size,
                    item.meanings.size
                )
                view.meaning.text = item.meanings.firstOrNull()?.translation?.text
            }
        }
    }

    class ExpandableWordHolder(v: View) :
        RecyclerView.ViewHolder(v) {

        private var view: CardView = v as CardView

        fun bindPayment(item: Word?, onExpand: (Word, Int) -> Unit, onClick: (Word) -> Unit) {
            item?.let {
                view.setOnClickListener { onExpand.invoke(item, adapterPosition) }
                view.title.text = item.text
                view.count.text = itemView.resources.getQuantityString(
                    R.plurals.meanings,
                    item.meanings.size,
                    item.meanings.size
                )

                view.setCardBackgroundColor(
                    if (item.expanded) itemView.resources.getColor(R.color.colorAccent) else itemView.resources.getColor(
                        R.color.white
                    )
                )

                val list = item.meanings.map {
                    Word(
                        item.id,
                        item.text,
                        listOf(it),
                        item.expanded
                    )
                }
                val adapter =
                    ExpandedWordAdapter(list, onClick)
                view.list.isVisible = item.expanded
                view.list.adapter = adapter

                view.meaning.text = item.meanings.joinToString { it.translation.text }
            }
        }
    }

    companion object {
        const val MEANING_ITEM = 0
        const val WORD_ITEM = 1

        private val COMPARATOR = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean =
                oldItem == newItem
        }
    }
}