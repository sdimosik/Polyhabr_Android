package ru.sdimosik.polyhabr.data.repository_impl

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import ru.sdimosik.polyhabr.common.utils.NetworkUtils
import ru.sdimosik.polyhabr.data.file.ReadFileResult
import ru.sdimosik.polyhabr.data.network.NetworkApi
import ru.sdimosik.polyhabr.data.network.model.article.ArticleCreateResponse
import ru.sdimosik.polyhabr.data.network.model.article.ArticleRequest
import ru.sdimosik.polyhabr.data.network.model.article.ArticleResponse
import ru.sdimosik.polyhabr.data.network.model.article_type.ArticleTypeListResponse
import ru.sdimosik.polyhabr.data.network.model.comment.CommentGetParam
import ru.sdimosik.polyhabr.data.network.model.comment.CommentRequest
import ru.sdimosik.polyhabr.data.network.model.comment.toDomain
import ru.sdimosik.polyhabr.data.network.model.discipline.DisciplineTypeListResponse
import ru.sdimosik.polyhabr.data.network.model.tag_type.TagTypeListResponse
import ru.sdimosik.polyhabr.data.network.model.tag_type.TagTypeRequest
import ru.sdimosik.polyhabr.data.network.model.tag_type.TagTypeResponse
import ru.sdimosik.polyhabr.data.network.model.user.*
import ru.sdimosik.polyhabr.data.network.param.ArticlesParam
import ru.sdimosik.polyhabr.data.network.param.SortArticleRequest
import ru.sdimosik.polyhabr.data.toDomain
import ru.sdimosik.polyhabr.domain.model.ArticleDomain
import ru.sdimosik.polyhabr.domain.model.ArticleListDomain
import ru.sdimosik.polyhabr.domain.model.CommentDomain
import ru.sdimosik.polyhabr.domain.model.CommentListDomain
import ru.sdimosik.polyhabr.domain.repository.INetworkRepository
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkApi: NetworkApi
) : INetworkRepository {
    override fun createArticle(articleRequest: ArticleRequest): Single<ArticleCreateResponse> {
        return networkApi.createArticle(articleRequest)
            .map(NetworkUtils::unwrap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getArticles(param: ArticlesParam): Single<ArticleListDomain> =
        networkApi.getArticles(param.offset, param.size, param.sorting ?: SortArticleRequest.EMPTY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(NetworkUtils::unwrap)
            .map { it.toDomain() }

    override fun getArticlesByUser(id: Long, offset: Int, size: Int): Single<ArticleListDomain> {
        return networkApi.getArticlesByUser(id = id, offset = offset, size = size)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(NetworkUtils::unwrap)
            .map { it.toDomain() }
    }

    override fun getMyArticle(offset: Int, size: Int): Single<ArticleListDomain> {
        return networkApi.getMyArticles(offset = offset, size = size)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(NetworkUtils::unwrap)
            .map { it.toDomain() }
    }

    override fun getFavouriteArticle(offset: Int, size: Int): Single<ArticleListDomain> {
        return networkApi.getFavouriteArticle(offset = offset, size = size)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(NetworkUtils::unwrap)
            .map { it.toDomain() }
    }

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

    override fun createComment(id: Long, text: String): Single<CommentDomain> {
        return networkApi.createComment(CommentRequest(articleId = id, text = text))
            .map(NetworkUtils::unwrap)
            .map { it.toDomain() }
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

    override fun verifyUser(code: String): Completable {
        return networkApi.verifyUser(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateUser(userUpdateRequest: UserUpdateRequest): Completable {
        return networkApi.updateUser(userUpdateRequest)
            .map(NetworkUtils::unwrap)
            .flatMapCompletable {
                if (it.isSuccess) {
                    Completable.complete()
                } else {
                    Completable.error(Throwable(it.message))
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun meUser(): Single<UserMeResponse> {
        return networkApi.meUser()
            .map(NetworkUtils::unwrap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTags(offset: Int, size: Int): Single<TagTypeListResponse> {
        return networkApi.getTags(offset = offset, size = size)
            .map(NetworkUtils::unwrap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun createTag(tagTypeRequest: TagTypeRequest): Single<TagTypeResponse> {
        return networkApi.createTag(tagTypeRequest)
            .map(NetworkUtils::unwrap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getArticleTypes(offset: Int, size: Int): Single<ArticleTypeListResponse> {
        return networkApi.getArticleTypes(offset = offset, size = size)
            .map(NetworkUtils::unwrap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getDisciplines(offset: Int, size: Int): Single<DisciplineTypeListResponse> {
        return networkApi.getDisciplines(offset = offset, size = size)
            .map(NetworkUtils::unwrap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun savePdfToArticle(
        articleId: Long,
        readFileResult: ReadFileResult,
    ): Completable {
        val bytes = readFileResult.bytes
        val fileName = readFileResult.fileName

        val fileRequestBody = bytes.toRequestBody()
        val articleRequestBody = articleId.toString().toRequestBody()

        val fullMultipartRequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", fileName, fileRequestBody)
            .addFormDataPart("articleId", articleId.toString())
            .build()
        return networkApi.savePdfToArticle(fullMultipartRequestBody)
            .flatMapCompletable {
                if (it.isSuccessful) {
                    Completable.complete()
                } else {
                    Completable.error(RuntimeException("Ошибка при сохранении файла"))
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun downFile(docId: String): Single<ResponseBody> {
        return networkApi.downFile(docId)
            .map(NetworkUtils::unwrap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}