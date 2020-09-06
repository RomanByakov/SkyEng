package com.rbyakov.skyeng.helpers

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.rbyakov.skyeng.network.models.State
import com.rbyakov.skyeng.network.models.Word

class WordBoundaryCallback(private val callback: MutableLiveData<State>) :
    PagedList.BoundaryCallback<Word>() {

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        callback.postValue(State.EMPTY)
    }
}