package ru.sdimosik.polyhabr.data.network

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*
import ru.sdimosik.polyhabr.data.network.model.article.ArticleListResponse
import ru.sdimosik.polyhabr.data.network.param.ArticlesParam
import ru.sdimosik.polyhabr.data.network.param.SortArticleRequest


interface NetworkApi {

    @POST("articles")
    fun getArticles(
        @Query("offset") offset: Int,
        @Query("size") size: Int,
        @Body sorting: SortArticleRequest = SortArticleRequest(),
    ): Single<Response<ArticleListResponse>>
}