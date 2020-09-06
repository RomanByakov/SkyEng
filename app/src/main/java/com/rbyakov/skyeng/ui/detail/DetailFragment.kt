package com.rbyakov.skyeng.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rbyakov.skyeng.ui.BaseFragment
import com.rbyakov.skyeng.vm.MainViewModel
import com.rbyakov.skyeng.R
import com.rbyakov.skyeng.helpers.loadImage
import com.rbyakov.skyeng.helpers.obtainViewModel
import com.rbyakov.skyeng.network.models.Word
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.word_item.title


class DetailFragment : BaseFragment() {

    companion object {
        private const val WORD = "word"

        fun newInstance(word: Word) = DetailFragment()
            .apply {
            arguments = Bundle().apply {
                putSerializable(WORD, word)
            }
        }
    }

    override val layoutRes: Int
        get() = R.layout.detail_fragment

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = requireActivity().obtainViewModel(MainViewModel::class.java)

        (activity as AppCompatActivity?)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        (arguments?.getSerializable(WORD) as? Word)?.let { word ->
            title.text = word.text
            word.meanings.firstOrNull()?.let {
                transcription.text = it.transcription
                meaning.text = it.translation.text
                note.text = it.translation.note
                image.loadImage(it.previewUrl, it.imageUrl)
            }
        }
    }

    override fun onBackPressed() {
        viewModel.exit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}