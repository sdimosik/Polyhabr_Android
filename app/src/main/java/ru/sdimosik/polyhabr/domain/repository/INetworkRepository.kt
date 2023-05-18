package ru.sdimosik.polyhabr.domain.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import ru.sdimosik.polyhabr.data.file.ReadFileResult
import ru.sdimosik.polyhabr.data.network.model.article.ArticleCreateResponse
import ru.sdimosik.polyhabr.data.network.model.article.ArticleRequest
import ru.sdimosik.polyhabr.data.network.model.article_type.ArticleTypeListResponse
import ru.sdimosik.polyhabr.data.network.model.comment.CommentGetParam
import ru.sdimosik.polyhabr.data.network.model.discipline.DisciplineTypeListResponse
import ru.sdimosik.polyhabr.data.network.model.tag_type.TagTypeListResponse
import ru.sdimosik.polyhabr.data.network.model.tag_type.TagTypeRequest
import ru.sdimosik.polyhabr.data.network.model.tag_type.TagTypeResponse
import ru.sdimosik.polyhabr.data.network.model.user.*
import ru.sdimosik.polyhabr.data.network.param.ArticlesParam
import ru.sdimosik.polyhabr.domain.model.ArticleDomain
import ru.sdimosik.polyhabr.domain.model.ArticleListDomain
import ru.sdimosik.polyhabr.domain.model.CommentDomain
import ru.sdimosik.polyhabr.domain.model.CommentListDomain

interface INetworkRepository {

    fun createArticle(articleRequest: ArticleRequest):  Single<ArticleCreateResponse>

    fun getArticles(param: ArticlesParam): Single<ArticleListDomain>

    fun getArticlesByUser(id: Long, offset: Int, size: Int): Single<ArticleListDomain>

    fun getMyArticle(offset: Int, size: Int): Single<ArticleListDomain>

    fun getFavouriteArticle(offset: Int, size: Int): Single<ArticleListDomain>

    fun addLike(id: Long): Completable

    fun decreaseLike(id: Long): Completable

    fun addToFavouriteArticle(id: Long): Completable

    fun removeFromFavouriteArticle(id: Long): Completable

    fun getDetailArticle(id: Long): Single<ArticleDomain>

    fun getCommentsByArticleId(commentGetParam: CommentGetParam): Single<CommentListDomain>

    fun createComment(id: Long, text: String): Single<CommentDomain>

    fun login(loginRequest: LoginRequest): Single<LoginResponse>

    fun register(newUser: NewUser): Completable

    fun checkFreeLogin(login: String): Completable

    fun checkFreeEmail(email: String): Completable

    fun verifyUser(code: String): Completable

    fun updateUser(userUpdateRequest: UserUpdateRequest): Completable

    fun meUser(): Single<UserMeResponse>

    fun getTags(offset: Int, size: Int): Single<TagTypeListResponse>

    fun createTag(tagTypeRequest: TagTypeRequest): Single<TagTypeResponse>

    fun getArticleTypes(offset: Int, size: Int): Single<ArticleTypeListResponse>

    fun getDisciplines(offset: Int, size: Int): Single<DisciplineTypeListResponse>

    fun savePdfToArticle(
        articleId: Long,
        readFileResult: ReadFileResult,
    ): Completable

    fun downFile(
       docId: String,
    ): Single<ResponseBody>
}