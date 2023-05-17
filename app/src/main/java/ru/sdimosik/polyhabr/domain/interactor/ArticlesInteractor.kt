package ru.sdimosik.polyhabr.domain.interactor

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.sdimosik.polyhabr.data.network.param.ArticlesParam
import ru.sdimosik.polyhabr.domain.model.ArticleDomain
import ru.sdimosik.polyhabr.domain.model.ArticleListDomain
import ru.sdimosik.polyhabr.domain.repository.INetworkRepository
import javax.inject.Inject

class ArticlesInteractor @Inject constructor(
    private val networkRepository: INetworkRepository
) : IArticlesInteractor {
    override fun getArticles(articlesParam: ArticlesParam): Single<ArticleListDomain> =
        networkRepository.getArticles(articlesParam)

    override fun getArticlesByUser(id: Long, offset: Int, size: Int): Single<ArticleListDomain> {
        return networkRepository.getArticlesByUser(id = id, offset = offset, size = size)
    }

    override fun getFavouriteArticle(offset: Int, size: Int): Single<ArticleListDomain> {
        return networkRepository.getFavouriteArticle(offset = offset, size = size)
    }

    override fun getMyArticle(offset: Int, size: Int): Single<ArticleListDomain> {
        return networkRepository.getMyArticle(offset = offset, size = size)
    }

    override fun changeLike(id: Long, addLike: Boolean): Completable {
        return if (addLike) {
            networkRepository.addLike(id)
        } else {
            networkRepository.decreaseLike(id)
        }
    }

    override fun changeFavourite(id: Long, addToFavourite: Boolean): Completable {
        return if (addToFavourite) {
            networkRepository.addToFavouriteArticle(id)
        } else {
            networkRepository.removeFromFavouriteArticle(id)
        }
    }

    override fun getDetailArticle(id: Long): Single<ArticleDomain> {
        return networkRepository.getDetailArticle(id)
    }
}

interface IArticlesInteractor {
    fun getArticles(articlesParam: ArticlesParam): Single<ArticleListDomain>

    fun getArticlesByUser(id: Long, offset: Int, size: Int): Single<ArticleListDomain>

    fun getFavouriteArticle(offset: Int, size: Int): Single<ArticleListDomain>

    fun getMyArticle(offset: Int, size: Int): Single<ArticleListDomain>

    fun changeLike(id: Long, addLike: Boolean): Completable

    fun changeFavourite(id: Long, addToFavourite: Boolean): Completable

    fun getDetailArticle(id: Long): Single<ArticleDomain>
}