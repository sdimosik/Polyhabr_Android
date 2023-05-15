package ru.sdimosik.polyhabr.data.repository_impl

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import ru.sdimosik.polyhabr.common.utils.NetworkUtils
import ru.sdimosik.polyhabr.data.network.NetworkApi
import ru.sdimosik.polyhabr.data.network.model.comment.CommentGetParam
import ru.sdimosik.polyhabr.data.network.model.comment.CommentRequest
import ru.sdimosik.polyhabr.data.network.model.comment.toDomain
import ru.sdimosik.polyhabr.data.network.model.user.LoginRequest
import ru.sdimosik.polyhabr.data.network.model.user.LoginResponse
import ru.sdimosik.polyhabr.data.network.model.user.NewUser
import ru.sdimosik.polyhabr.data.network.param.ArticlesParam
import ru.sdimosik.polyhabr.data.network.param.SortArticleRequest
import ru.sdimosik.polyhabr.data.toDomain
import ru.sdimosik.polyhabr.domain.model.ArticleDomain
import ru.sdimosik.polyhabr.domain.model.ArticleListDomain
import ru.sdimosik.polyhabr.domain.model.CommentListDomain
import ru.sdimosik.polyhabr.domain.repository.INetworkRepository
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkApi: NetworkApi
) : INetworkRepository {
    override fun getArticles(param: ArticlesParam): Single<ArticleListDomain> =
        networkApi.getArticles(param.offset, param.size, param.sorting ?: SortArticleRequest.EMPTY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(NetworkUtils::unwrap)
            .map { it.toDomain() }

    override fun addLike(id: Long): Completable =
        networkApi.addLike(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun decreaseLike(id: Long): Completable =
        networkApi.decreaseLike(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun addToFavouriteArticle(id: Long): Completable =
        networkApi.addToFavouriteArticle(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun removeFromFavouriteArticle(id: Long): Completable =
        networkApi.removeFromFavouriteArticle(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getDetailArticle(id: Long): Single<ArticleDomain> =
        networkApi.getDetailArticle(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(NetworkUtils::unwrap)
            .map { it.toDomain() }

    override fun getCommentsByArticleId(commentGetParam: CommentGetParam): Single<CommentListDomain> {
        return networkApi.getCommentsByArticleId(
            offset = commentGetParam.offset,
            size = commentGetParam.size,
            id = commentGetParam.articleId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(NetworkUtils::unwrap)
            .map { it.toDomain() }
    }

    override fun createComment(id: Long, text: String): Completable {
        return networkApi.createComment(CommentRequest(articleId = id, text = text))
            .map(NetworkUtils::unwrap)
            .flatMapCompletable {
                if (it.isSuccess) {
                    Completable.complete()
                } else {
                    Completable.error(Throwable("Ошибка при создании комментария"))
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun login(loginRequest: LoginRequest): Single<LoginResponse> {
        return networkApi.signin(loginRequest)
            .map(NetworkUtils::unwrap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun register(newUser: NewUser): Completable {
        return networkApi.signup(newUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun checkFreeLogin(login: String): Completable {
        return networkApi.checkFreeLogin(login)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun checkFreeEmail(email: String): Completable {
        return networkApi.checkFreeEmail(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}