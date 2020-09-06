package com.rbyakov.skyeng.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rbyakov.skyeng.Screens
import com.rbyakov.skyeng.helpers.WordBoundaryCallback
import com.rbyakov.skyeng.network.models.State
import com.rbyakov.skyeng.network.models.Word
import com.rbyakov.skyeng.repo.WordDataSourceFactory
import com.rbyakov.skyeng.repo.WordRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val router: Router,
    private val wordRepo: WordRepo
) : ViewModel() {

    private val job = Job()
    private val scope = CoroutineScope(job)

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(20)
        .build()

    val stateLiveData = MutableLiveData(State.SUCCESS)

    private val sourceFactory =
        WordDataSourceFactory(
            state = stateLiveData, wordRepo = wordRepo,
            coroutineContext = scope.coroutineContext
        )

    val pagedListLiveData: LiveData<PagedList<Word>> =
        LivePagedListBuilder(sourceFactory, config)
            .setBoundaryCallback(
                WordBoundaryCallback(
                    stateLiveData
                )
            )
            .build()

    fun search(text: String) {
        if (sourceFactory.text != text) {
            sourceFactory.text = text
            pagedListLiveData.value?.dataSource?.invalidate()
        }
    }

    fun navigateToDetail(word: Word) {
        router.navigateTo(Screens.DetailScreen(word))
    }

    fun exit() {
        router.exit()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}