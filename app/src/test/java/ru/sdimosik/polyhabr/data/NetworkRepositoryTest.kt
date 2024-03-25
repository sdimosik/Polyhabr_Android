package ru.sdimosik.polyhabr.data

import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import retrofit2.Response
import ru.sdimosik.polyhabr.common.utils.NetworkUtils
import ru.sdimosik.polyhabr.data.file.ReadFileResult
import ru.sdimosik.polyhabr.data.network.NetworkApi
import ru.sdimosik.polyhabr.data.network.model.article.ArticleCreateResponse
import ru.sdimosik.polyhabr.data.network.model.article.ArticleListResponse
import ru.sdimosik.polyhabr.data.network.model.article.ArticleRequest
import ru.sdimosik.polyhabr.data.network.model.article.ArticleResponse
import ru.sdimosik.polyhabr.data.network.model.article_type.ArticleTypeListResponse
import ru.sdimosik.polyhabr.data.network.model.comment.CommentGetParam
import ru.sdimosik.polyhabr.data.network.model.comment.CommentListResponse
import ru.sdimosik.polyhabr.data.network.model.comment.CommentRequest
import ru.sdimosik.polyhabr.data.network.model.comment.CommentResponse
import ru.sdimosik.polyhabr.data.network.model.comment.toDomain
import ru.sdimosik.polyhabr.data.network.model.discipline.DisciplineTypeListResponse
import ru.sdimosik.polyhabr.data.network.model.file.FileResponse
import ru.sdimosik.polyhabr.data.network.model.tag_type.TagTypeListResponse
import ru.sdimosik.polyhabr.data.network.model.user.LoginRequest
import ru.sdimosik.polyhabr.data.network.model.user.LoginResponse
import ru.sdimosik.polyhabr.data.network.model.user.NewUser
import ru.sdimosik.polyhabr.data.network.model.user.UserMeResponse
import ru.sdimosik.polyhabr.data.network.model.user.UserOtherResponse
import ru.sdimosik.polyhabr.data.network.model.user.UserUpdateRequest
import ru.sdimosik.polyhabr.data.network.model.user.UserUpdateResponse
import ru.sdimosik.polyhabr.data.network.param.ArticlesParam
import ru.sdimosik.polyhabr.data.network.param.SortArticleRequest
import ru.sdimosik.polyhabr.data.repository_impl.NetworkRepository
import ru.sdimosik.polyhabr.domain.model.ArticleListDomain
import ru.sdimosik.polyhabr.domain.model.CommentListDomain
import ru.sdimosik.polyhabr.domain.repository.INetworkRepository

class NetworkRepositoryUnitTest {

    private val networkApi: NetworkApi = mock()

    private lateinit var networkRepository: INetworkRepository

    val offset = 0
    val size = 10
    val sorting = SortArticleRequest()
    val id = 1L
    val articleResponse = ArticleResponse(
        id = null,
        date = null,
        filePdf = null,
        likes = null,
        previewText = null,
        typeId = null,
        user = null,
        title = null,
        text = null,
        listDisciplineName = null,
        listTag = emptyList(),
        fileId = null,
        viewCount = null,
        isSaveToFavourite = null,
        pdfId = null,
        previewImgId = null,
        isLiked = null
    )
    val articleDomain = articleResponse.toDomain()

    val commentListResponse = CommentListResponse(
        contents = emptyList(),
        totalElements = 0,
        totalPages = 0,
    )
    val commentListDomain = CommentListDomain(
        contents = emptyList(),
        totalElements = 0,
        totalPages = 0,
    )
    val commentResponse = CommentResponse(
        id = 1,
        text = "text",
        articleId = 1,
        userId = UserOtherResponse(1, "name", "avatar", "email"),
        data = "data",
    )
    val commentRequest = CommentRequest(
        text = "text",
        articleId = 1,
    )
    val commentDomain = commentResponse.toDomain()

    private companion object {
        @BeforeAll
        @JvmStatic
        fun setupSchedulers() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        }
    }

    @BeforeEach
    fun beforeEach() {
        networkRepository = NetworkRepository(networkApi)
    }

    @Test
    fun `createArticle should return ArticleCreateResponse`() {
        val articleRequest = mock<ArticleRequest>()
        val articleCreateResponse = mock<ArticleCreateResponse>()
        whenever(networkApi.createArticle(articleRequest)).thenReturn(
            Single.just(
                Response.success(articleCreateResponse)
            )
        )

        val result = networkRepository.createArticle(articleRequest).test()

        result.assertValue(articleCreateResponse)
    }

    @Test
    fun `getArticles should return ArticleListDomain`() {
        val articleListResponse = ArticleListResponse(
            contents = emptyList(),
            totalElements = 0,
            totalPages = 0,
        )
        val articleListDomain = ArticleListDomain(
            contents = emptyList(),
            totalElements = 0,
            totalPages = 0,
        )
        whenever(networkApi.getArticles(offset, size, sorting)).thenReturn(
            Single.just(
                Response.success(articleListResponse)
            )
        )

        val result = networkRepository.getArticles(ArticlesParam(offset, size, sorting)).test()

        result.assertValue(articleListDomain)
    }

    @Test
    fun `getArticlesByUser should return ArticleListDomain`() {
        val articleListResponse = ArticleListResponse(
            contents = emptyList(),
            totalElements = 0,
            totalPages = 0,
        )
        val articleListDomain = ArticleListDomain(
            contents = emptyList(),
            totalElements = 0,
            totalPages = 0,
        )
        whenever(networkApi.getArticlesByUser(id, offset, size)).thenReturn(
            Single.just(
                Response.success(articleListResponse)
            )
        )

        val result = networkRepository.getArticlesByUser(id, offset, size).test()

        result.assertValue(articleListDomain)
    }

    @Test
    fun `getMyArticle should return ArticleListDomain`() {
        val articleListResponse = ArticleListResponse(
            contents = emptyList(),
            totalElements = 0,
            totalPages = 0,
        )
        val articleListDomain = ArticleListDomain(
            contents = emptyList(),
            totalElements = 0,
            totalPages = 0,
        )
        whenever(networkApi.getMyArticles(offset, size)).thenReturn(
            Single.just(
                Response.success(articleListResponse)
            )
        )

        val result = networkRepository.getMyArticle(offset, size).test()

        result.assertValue(articleListDomain)
    }

    @Test
    fun `getFavouriteArticle should return ArticleListDomain`() {
        val articleListResponse = ArticleListResponse(
            contents = emptyList(),
            totalElements = 0,
            totalPages = 0,
        )
        val articleListDomain = ArticleListDomain(
            contents = emptyList(),
            totalElements = 0,
            totalPages = 0,
        )
        whenever(networkApi.getFavouriteArticle(offset, size)).thenReturn(
            Single.just(
                Response.success(articleListResponse)
            )
        )

        val result = networkRepository.getFavouriteArticle(offset, size).test()

        result.assertValue(articleListDomain)
    }

    @Test
    fun `addLike should return Completable`() {
        whenever(networkApi.addLike(id)).thenReturn(
            Completable.complete()
        )

        val result = networkRepository.addLike(id).test()

        result.assertComplete()
    }

    @Test
    fun `decreaseLike should return Completable`() {
        whenever(networkApi.decreaseLike(id)).thenReturn(
            Completable.complete()
        )

        val result = networkRepository.decreaseLike(id).test()

        result.assertComplete()
    }

    @Test
    fun `addToFavouriteArticle should return Completable`() {
        whenever(networkApi.addToFavouriteArticle(id)).thenReturn(
            Completable.complete()
        )

        val result = networkRepository.addToFavouriteArticle(id).test()

        result.assertComplete()
    }

    @Test
    fun `removeFromFavouriteArticle should return Completable`() {
        whenever(networkApi.removeFromFavouriteArticle(id)).thenReturn(
            Completable.complete()
        )

        val result = networkRepository.removeFromFavouriteArticle(id).test()

        result.assertComplete()
    }

    @Test
    fun `getDetailArticle should return ArticleDomain`() {
        whenever(networkApi.getDetailArticle(id)).thenReturn(
            Single.just(
                Response.success(articleResponse)
            )
        )

        val result = networkRepository.getDetailArticle(id).test()

        result.assertValue(articleDomain)
    }

    @Test
    fun `getCommentsByArticleId should return CommentListDomain`() {
        whenever(networkApi.getCommentsByArticleId(offset, size, id)).thenReturn(
            Single.just(
                Response.success(commentListResponse)
            )
        )

        val result =
            networkRepository.getCommentsByArticleId(CommentGetParam(id, offset, size)).test()

        result.assertValue(commentListDomain)
    }

    @Test
    fun `getTags should return TagTypeListResponse`() {
        val tagTypeListResponse = mock<TagTypeListResponse>()
        whenever(networkApi.getTags(offset, size)).thenReturn(
            Single.just(
                Response.success(tagTypeListResponse)
            )
        )

        val result = networkRepository.getTags(offset, size).test()

        result.assertValue(tagTypeListResponse)
    }

    @Test
    fun `getArticleTypes should return ArticleTypeListResponse`() {
        val articleTypeListResponse = mock<ArticleTypeListResponse>()
        whenever(networkApi.getArticleTypes(offset, size)).thenReturn(
            Single.just(
                Response.success(articleTypeListResponse)
            )
        )

        val result = networkRepository.getArticleTypes(offset, size).test()

        result.assertValue(articleTypeListResponse)
    }

    @Test
    fun `getDisciplines should return DisciplineTypeListResponse`() {
        val disciplineTypeListResponse = mock<DisciplineTypeListResponse>()
        whenever(networkApi.getDisciplines(offset, size)).thenReturn(
            Single.just(
                Response.success(disciplineTypeListResponse)
            )
        )

        val result = networkRepository.getDisciplines(offset, size).test()

        result.assertValue(disciplineTypeListResponse)
    }

    @Test
    fun `createComment should return CommentDomain`() {
        whenever(networkApi.createComment(any())).thenReturn(
            Single.just(
                Response.success(commentResponse)
            )
        )

        val result =
            networkRepository.createComment(commentRequest.articleId!!, commentResponse.text).test()

        result.assertValue(commentDomain)
    }

    @Test
    fun `login should return LoginResponse`() {
        val loginRequest = mock<LoginRequest>()
        val loginResponse = mock<LoginResponse>()
        whenever(networkApi.signin(loginRequest)).thenReturn(
            Single.just(
                Response.success(loginResponse)
            )
        )

        val result = networkRepository.login(loginRequest).test()

        result.assertValue(loginResponse)
    }

    @Test
    fun `register should return Completable`() {
        val newUser = mock<NewUser>()
        whenever(networkApi.signup(newUser)).thenReturn(
            Completable.complete()
        )

        val result = networkRepository.register(newUser).test()

        result.assertComplete()
    }

    @Test
    fun `checkFreeLogin should return Completable`() {
        val login = "login"
        whenever(networkApi.checkFreeLogin(login)).thenReturn(
            Completable.complete()
        )

        val result = networkRepository.checkFreeLogin(login).test()

        result.assertComplete()
    }

    @Test
    fun `checkFreeEmail should return Completable`() {
        val email = "email"
        whenever(networkApi.checkFreeEmail(email)).thenReturn(
            Completable.complete()
        )

        val result = networkRepository.checkFreeEmail(email).test()

        result.assertComplete()
    }

    @Test
    fun `verifyUser should return Completable`() {
        val code = "code"
        whenever(networkApi.verifyUser(code)).thenReturn(
            Completable.complete()
        )

        val result = networkRepository.verifyUser(code).test()

        result.assertComplete()
    }

    @Test
    fun `updateUser should return Completable`() {
        val userUpdateRequest = mock<UserUpdateRequest>()
        val userUpdateResponse = UserUpdateResponse(
            isSuccess = true,
            message = "message",
        )
        whenever(networkApi.updateUser(userUpdateRequest)).thenReturn(
            Single.just(
                Response.success(userUpdateResponse)
            )
        )

        val result = networkRepository.updateUser(userUpdateRequest).test()

        result.assertComplete()
    }

    @Test
    fun `meUser should return UserMeResponse`() {
        val userMeResponse = mock<UserMeResponse>()
        whenever(networkApi.meUser()).thenReturn(
            Single.just(
                Response.success(userMeResponse)
            )
        )

        val result = networkRepository.meUser().test()

        result.assertValue(userMeResponse)
    }

    @Test
    fun `downFile should return ResponseBody`() {
        val docId = "docId"
        val responseBody = mock<ResponseBody>()
        whenever(networkApi.downFile(docId)).thenReturn(
            Single.just(
                Response.success(responseBody)
            )
        )

        val result = networkRepository.downFile(docId).test()

        result.assertValue(responseBody)
    }
}