package ru.sdimosik.polyhabr.domain.interactor

import io.reactivex.rxjava3.core.Single
import ru.sdimosik.polyhabr.data.network.param.ArticlesParam
import ru.sdimosik.polyhabr.domain.model.ArticleListDomain
import ru.sdimosik.polyhabr.domain.repository.INetworkRepository
import javax.inject.Inject

class ArticlesInteractor @Inject constructor(
    private val networkRepository: INetworkRepository
) : IArticlesInteractor {
    override fun getArticles(articlesParam: ArticlesParam): Single<ArticleListDomain> =
        networkRepository.getArticles(articlesParam)
}

interface IArticlesInteractor {
    fun getArticles(articlesParam: ArticlesParam): Single<ArticleListDomain>
}