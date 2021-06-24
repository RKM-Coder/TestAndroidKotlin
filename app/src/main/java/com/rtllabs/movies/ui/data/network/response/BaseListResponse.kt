package com.rtllabs.movies.ui.data.network.response

import com.google.gson.annotations.SerializedName

open class BaseListResponse<Item> {

    @SerializedName("total_pages") val totalPages: Int? = null
    @SerializedName("total_results") val totalResults: Int? = null
    @SerializedName("results") val results: List<Item>? = null
    @SerializedName("page") val page: Int? = null
}