package ru.sdimosik.polyhabr.data.network

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import ru.sdimosik.polyhabr.data.network.model.article.ArticleCreateResponse
import ru.sdimosik.polyhabr.data.network.model.article.ArticleListResponse
import ru.sdimosik.polyhabr.data.network.model.article.ArticleRequest
import ru.sdimosik.polyhabr.data.network.model.article.ArticleResponse
import ru.sdimosik.polyhabr.data.network.model.article_type.ArticleTypeListResponse
import ru.sdimosik.polyhabr.data.network.model.comment.CommentCreateResponse
import ru.sdimosik.polyhabr.data.network.model.comment.CommentListResponse
import ru.sdimosik.polyhabr.data.network.model.comment.CommentRequest
import ru.sdimosik.polyhabr.data.network.model.comment.CommentResponse
import ru.sdimosik.polyhabr.data.network.model.discipline.DisciplineTypeListResponse
import ru.sdimosik.polyhabr.data.network.model.file.FileResponse
import ru.sdimosik.polyhabr.data.network.model.tag_type.TagTypeListResponse
import ru.sdimosik.polyhabr.data.network.model.tag_type.TagTypeRequest
import ru.sdimosik.polyhabr.data.network.model.tag_type.TagTypeResponse
import ru.sdimosik.polyhabr.data.network.model.user.*
import ru.sdimosik.polyhabr.data.network.param.ArticlesParam
import ru.sdimosik.polyhabr.data.network.param.SortArticleRequest


interface NetworkApi {

    @POST("articles/create")
    fun createArticle(@Body articleRequest: ArticleRequest): Single<Response<ArticleCreateResponse>>

    @POST("articles")
    fun getArticles(
        @Query("offset") offset: Int,
        @Query("size") size: Int,
        @Body sorting: SortArticleRequest = SortArticleRequest(),
    ): Single<Response<ArticleListResponse>>

    @GET("articles/byUser")
    fun getArticlesByUser(
        @Query("id") id: Long,
        @Query("offset") offset: Int,
        @Query("size") size: Int,
    ): Single<Response<ArticleListResponse>>

    @GET("articles/my")
    fun getMyArticles(
        @Query("offset") offset: Int,
        @Query("size") size: Int,
    ): Single<Response<ArticleListResponse>>

    @GET("articles/getFavArticles")
    fun getFavouriteArticle(
        @Query("offset") offset: Int,
        @Query("size") size: Int,
    ): Single<Response<ArticleListResponse>>

    @POST("articles/add_like")
    fun addLike(
        @Query("articleId") id: Long,
    ): Completable

    @POST("articles/decrease_like")
    fun decreaseLike(
        @Query("articleId") id: Long,
    ): Completable

    @POST("articles/addFavArticles")
    fun addToFavouriteArticle(
        @Query("articleId") id: Long,
    ): Completable

    @POST("articles/removeFromArticles")
    fun removeFromFavouriteArticle(
        @Query("articleId") id: Long,
    ): Completable

    @GET("articles/byId")
    fun getDetailArticle(
        @Query("id") id: Long,
    ): Single<Response<ArticleResponse>>

    @GET("comment/byArticleId")
    fun getCommentsByArticleId(
        @Query("offset") offset: Int,
        @Query("size") size: Int,
        @Query("articleId") id: Long,
    ): Single<Response<CommentListResponse>>

    @POST("comment/create")
    fun createComment(@Body param: CommentRequest): Single<Response<CommentResponse>>

    @POST("api/auth/signupmob")
    fun signup(@Body newUser: NewUser): Completable

    @POST("api/auth/signin")
    fun signin(@Body loginRequest: LoginRequest): Single<Response<LoginResponse>>

    @GET("api/auth/checkFreeLogin")
    fun checkFreeLogin(@Query("login") login: String): Completable

    @GET("api/auth/checkFreeEmail")
    fun checkFreeEmail(@Query("email") email: String): Completable

    @GET("api/auth/verify")
    fun verifyUser(@Query("code") code: String): Completable

    @PUT("users/update")
    fun updateUser(@Body userUpdateRequest: UserUpdateRequest): Single<Response<UserUpdateResponse>>

    @GET("users/me")
    fun meUser(): Single<Response<UserMeResponse>>

    @GET("tag_type")
    fun getTags(
        @Query("offset") offset: Int,
        @Query("size") size: Int
    ): Single<Response<TagTypeListResponse>>

    @POST("tag_type/create")
    fun createTag(
        @Body tagTypeRequest: TagTypeRequest
    ): Single<Response<TagTypeResponse>>

    @GET("article_types")
    fun getArticleTypes(
        @Query("offset") offset: Int,
        @Query("size") size: Int
    ): Single<Response<ArticleTypeListResponse>>

    @GET("discipline_type")
    fun getDisciplines(
        @Query("offset") offset: Int,
        @Query("size") size: Int
    ): Single<Response<DisciplineTypeListResponse>>


    @POST("files")
    fun savePdfToArticle(
        @Body multipartBody: RequestBody,
    ): Single<Response<FileResponse>>

    @GET("files/{Id}/download")
    fun downFile(
        @Path("Id") docId: String,
    ): Single<Response<ResponseBody>>
}