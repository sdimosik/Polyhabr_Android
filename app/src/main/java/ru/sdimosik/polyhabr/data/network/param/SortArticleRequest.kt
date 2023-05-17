package ru.sdimosik.polyhabr.data.network.param

import com.google.gson.annotations.SerializedName

data class SortArticleRequest(
    @SerializedName("fieldView")
    var fieldView: Boolean? = null,
    @SerializedName("fieldRating")
    var fieldRating: Boolean? = null,
    @SerializedName("datRange")
    var datRange: String? = null,
) {
    companion object{
        val EMPTY = SortArticleRequest()
    }
}
