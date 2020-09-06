package com.rbyakov.skyeng.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rbyakov.skyeng.network.models.State
import com.rbyakov.skyeng.network.models.Word
import kotlin.coroutines.CoroutineContext

class WordDataSourceFactory(
    var text: String? = null,
    val wordRepo: WordRepo,
    val state: MutableLiveData<State>,
    val coroutineContext: CoroutineContext
) :
    DataSource.Factory<Int, Word>() {

    override fun create(): DataSource<Int, Word> {
        return WordDataSource(
            text,
            wordRepo,
            state,
            coroutineContext
        )
    }
}