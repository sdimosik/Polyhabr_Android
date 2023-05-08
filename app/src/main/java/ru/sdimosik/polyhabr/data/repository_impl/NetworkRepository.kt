package ru.sdimosik.polyhabr.data.repository_impl

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.sdimosik.polyhabr.common.utils.NetworkUtils
import ru.sdimosik.polyhabr.data.network.NetworkApi
import ru.sdimosik.polyhabr.data.network.param.ArticlesParam
import ru.sdimosik.polyhabr.data.network.param.SortArticleRequest
import ru.sdimosik.polyhabr.data.toDomain
import ru.sdimosik.polyhabr.domain.model.ArticleListDomain
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
}