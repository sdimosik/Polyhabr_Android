package ru.sdimosik.polyhabr.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.sdimosik.polyhabr.data.network.param.ArticlesParam
import ru.sdimosik.polyhabr.domain.model.ArticleListDomain

interface INetworkRepository {
    fun getArticles(param: ArticlesParam): Single<ArticleListDomain>
}