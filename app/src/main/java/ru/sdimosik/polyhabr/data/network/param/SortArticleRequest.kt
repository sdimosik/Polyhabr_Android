package ru.sdimosik.polyhabr.data.network.param

data class SortArticleRequest(
    var fieldView: Boolean? = null,
    var fieldRating: Boolean? = null,
    var datRange: String? = null,
) {
    companion object{
        val EMPTY = SortArticleRequest()
    }
}
