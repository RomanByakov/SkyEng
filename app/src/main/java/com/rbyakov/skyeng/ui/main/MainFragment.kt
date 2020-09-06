package com.rbyakov.skyeng.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.rbyakov.skyeng.ui.BaseFragment
import com.rbyakov.skyeng.vm.MainViewModel
import com.rbyakov.skyeng.R
import com.rbyakov.skyeng.helpers.obtainViewModel
import com.rbyakov.skyeng.network.models.State
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : BaseFragment() {

    private lateinit var viewModel: MainViewModel

    override val layoutRes: Int
        get() = R.layout.main_fragment

    private val adapter = WordAdapter({ word ->
        viewModel.navigateToDetail(word)
    }, { word, index ->
        word.expanded = !word.expanded
        meanings.adapter?.notifyItemChanged(index)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = requireActivity().obtainViewModel(MainViewModel::class.java)

        (activity as AppCompatActivity?)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }

        meanings.adapter = adapter
        viewModel.pagedListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        searchEt.doAfterTextChanged {
            viewModel.search(it.toString())
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer { state ->
            progress.isVisible = false
            when (state) {
                State.EMPTY -> {
                    emptyList.text = getString(R.string.no_results)
                    meanings.isVisible = false
                    emptyList.isVisible = true
                }
                State.SUCCESS -> {
                    meanings.isVisible = true
                    emptyList.isVisible = false
                }
                State.LOADING -> {
                    progress.isVisible = true
                }
                State.ERROR -> {
                    emptyList.text = getString(R.string.error)
                    meanings.isVisible = false
                    emptyList.isVisible = true
                }
                else -> {
                }
            }
        })
    }

    override fun onBackPressed() {
        viewModel.exit()
    }
}