package ru.sdimosik.polyhabr.data.network.param

import com.google.gson.annotations.SerializedName

data class ArticlesParam(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("sorting")
    val sorting: SortArticleRequest? = null,
)
