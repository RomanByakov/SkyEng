package com.rbyakov.skyeng.repo

import com.rbyakov.skyeng.network.Api
import javax.inject.Inject

class WordRepo @Inject constructor(private val api: Api) {

    // can store and show previous results on init
    suspend fun search(search: String, page: Int, pageSize: Int) =
        api.search(search, page, pageSize)

}