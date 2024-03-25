package ru.sdimosik.polyhabr.domain

import com.nhaarman.mockitokotlin2.verify
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import ru.sdimosik.polyhabr.data.network.model.article.ArticleRequest
import ru.sdimosik.polyhabr.data.network.model.tag_type.TagTypeRequest
import ru.sdimosik.polyhabr.data.network.param.ArticlesParam
import ru.sdimosik.polyhabr.domain.interactor.ArticlesInteractor
import ru.sdimosik.polyhabr.domain.interactor.IArticlesInteractor
import ru.sdimosik.polyhabr.domain.repository.INetworkRepository

class ArticlesInteractorTest {

    private lateinit var articlesInteractor: IArticlesInteractor
    private val networkRepository: INetworkRepository = mock()

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
        articlesInteractor = ArticlesInteractor(networkRepository)
    }

    @Test
    fun `createArticle should call networkRepository createArticle`() {
        // given
        val articleRequest = mock<ArticleRequest>()
        // when
        articlesInteractor.createArticle(articleRequest)
        // then
        verify(networkRepository).createArticle(articleRequest)
    }

    @Test
    fun `getArticles should call networkRepository getArticles`() {
        // given
        val articlesParam = mock<ArticlesParam>()
        // when
        articlesInteractor.getArticles(articlesParam)
        // then
        verify(networkRepository).getArticles(articlesParam)
    }

    @Test
    fun `getArticlesByUser should call networkRepository getArticlesByUser`() {
        // given
        val id = 1L
        val offset = 0
        val size = 10
        // when
        articlesInteractor.getArticlesByUser(id, offset, size)
        // then
        verify(networkRepository).getArticlesByUser(id, offset, size)
    }

    @Test
    fun `getFavouriteArticle should call networkRepository getFavouriteArticle`() {
        // given
        val offset = 0
        val size = 10
        // when
        articlesInteractor.getFavouriteArticle(offset, size)
        // then
        verify(networkRepository).getFavouriteArticle(offset, size)
    }

    @Test
    fun `getMyArticle should call networkRepository getMyArticle`() {
        // given
        val offset = 0
        val size = 10
        // when
        articlesInteractor.getMyArticle(offset, size)
        // then
        verify(networkRepository).getMyArticle(offset, size)
    }

    @Test
    fun `changeLike should call networkRepository addLike when addLike is true`() {
        // given
        val id = 1L
        val addLike = true
        // when
        articlesInteractor.changeLike(id, addLike)
        // then
        verify(networkRepository).addLike(id)
    }

    @Test
    fun `changeLike should call networkRepository decreaseLike when addLike is false`() {
        // given
        val id = 1L
        val addLike = false
        // when
        articlesInteractor.changeLike(id, addLike)
        // then
        verify(networkRepository).decreaseLike(id)
    }

    @Test
    fun `changeFavourite should call networkRepository addToFavouriteArticle when addToFavourite is true`() {
        // given
        val id = 1L
        val addToFavourite = true
        // when
        articlesInteractor.changeFavourite(id, addToFavourite)
        // then
        verify(networkRepository).addToFavouriteArticle(id)
    }

    @Test
    fun `changeFavourite should call networkRepository removeFromFavouriteArticle when addToFavourite is false`() {
        // given
        val id = 1L
        val addToFavourite = false
        // when
        articlesInteractor.changeFavourite(id, addToFavourite)
        // then
        verify(networkRepository).removeFromFavouriteArticle(id)
    }

    @Test
    fun `getDetailArticle should call networkRepository getDetailArticle`() {
        // given
        val id = 1L
        // when
        articlesInteractor.getDetailArticle(id)
        // then
        verify(networkRepository).getDetailArticle(id)
    }

    @Test
    fun `getTags should call networkRepository getTags`() {
        // given
        val offset = 0
        val size = 10
        // when
        articlesInteractor.getTags(offset, size)
        // then
        verify(networkRepository).getTags(offset, size)
    }

    @Test
    fun `createTag should call networkRepository createTag`() {
        // given
        val tagTypeRequest = mock<TagTypeRequest>()
        // when
        articlesInteractor.createTag(tagTypeRequest)
        // then
        verify(networkRepository).createTag(tagTypeRequest)
    }

    @Test
    fun `getArticleTypes should call networkRepository getArticleTypes`() {
        // given
        val offset = 0
        val size = 10
        // when
        articlesInteractor.getArticleTypes(offset, size)
        // then
        verify(networkRepository).getArticleTypes(offset, size)
    }

    @Test
    fun `getDisciplines should call networkRepository getDisciplines`() {
        // given
        val offset = 0
        val size = 10
        // when
        articlesInteractor.getDisciplines(offset, size)
        // then
        verify(networkRepository).getDisciplines(offset, size)
    }
}