package com.rbyakov.skyeng.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.rbyakov.skyeng.network.models.State
import com.rbyakov.skyeng.network.models.Word
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class WordDataSource(
    private val text: String?,
    private val wordRepo: WordRepo,
    private val state: MutableLiveData<State>,
    private val coroutineContext: CoroutineContext
) :
    PageKeyedDataSource<Int, Word>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Word>
    ) {
        if (!text.isNullOrEmpty()) {
            scope.launch {
                delay(300)
                state.postValue(State.LOADING)
                try {
                    val response = wordRepo.search(text, 1, 20)

                    if (response.isSuccessful) {
                        response.body()?.let {
                            callback.onResult(it, 1, 2)
                            state.postValue(State.SUCCESS)
                        }
                    }
                } catch (e: CancellationException) {
                } catch (e: Exception) {
                    e.printStackTrace()
                    state.postValue(State.ERROR)
                }
            }
        } else {
            state.postValue(State.SUCCESS)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Word>) {
        text?.let {
            scope.launch {
                state.postValue(State.LOADING)
                try {
                    val response = wordRepo.search(text, params.key, 20)

                    if (response.isSuccessful) {
                        response.body()?.let {
                            callback.onResult(it, params.key + 1)
                            state.postValue(State.SUCCESS)
                        }
                    }
                } catch (e: CancellationException) {
                } catch (e: Exception) {
                    e.printStackTrace()
                    state.postValue(State.ERROR)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Word>) {

    }

    override fun invalidate() {
        job.cancel()
        super.invalidate()
    }
}