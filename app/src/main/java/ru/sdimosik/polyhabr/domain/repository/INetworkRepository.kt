package ru.sdimosik.polyhabr.domain.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import ru.sdimosik.polyhabr.data.network.model.comment.CommentGetParam
import ru.sdimosik.polyhabr.data.network.model.user.LoginRequest
import ru.sdimosik.polyhabr.data.network.model.user.LoginResponse
import ru.sdimosik.polyhabr.data.network.model.user.NewUser
import ru.sdimosik.polyhabr.data.network.param.ArticlesParam
import ru.sdimosik.polyhabr.domain.model.ArticleDomain
import ru.sdimosik.polyhabr.domain.model.ArticleListDomain
import ru.sdimosik.polyhabr.domain.model.CommentListDomain

interface INetworkRepository {
    fun getArticles(param: ArticlesParam): Single<ArticleListDomain>

    fun addLike(id: Long): Completable

    fun decreaseLike(id: Long): Completable

    fun addToFavouriteArticle(id: Long): Completable

    fun removeFromFavouriteArticle(id: Long): Completable

    fun getDetailArticle(id: Long): Single<ArticleDomain>

    fun getCommentsByArticleId(commentGetParam: CommentGetParam): Single<CommentListDomain>

    fun createComment(id: Long, text: String): Completable

    fun login(loginRequest: LoginRequest): Single<LoginResponse>

    fun register(newUser: NewUser): Completable

    fun checkFreeLogin(login: String): Completable

    fun checkFreeEmail(email: String): Completable
}