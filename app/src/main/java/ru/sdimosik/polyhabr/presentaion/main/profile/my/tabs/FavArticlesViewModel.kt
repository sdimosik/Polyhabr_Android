package ru.sdimosik.polyhabr.presentaion.main.profile.my.tabs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.sdimosik.polyhabr.common.ui.BaseViewModel
import ru.sdimosik.polyhabr.data.network.param.ArticlesParam
import ru.sdimosik.polyhabr.domain.interactor.IArticlesInteractor
import ru.sdimosik.polyhabr.domain.interactor.IAuthInteractor
import ru.sdimosik.polyhabr.domain.model.ArticleDomain
import ru.sdimosik.polyhabr.domain.model.toUI
import ru.sdimosik.polyhabr.presentaion.main.feed.adapter.ArticleItem
import ru.sdimosik.polyhabr.utils.ErrorHelper
import ru.sdimosik.polyhabr.utils.loading
import javax.inject.Inject

@HiltViewModel
class FavArticlesViewModel @Inject constructor(
    private val articlesInteractor: IArticlesInteractor,
    private val authInteractor: IAuthInteractor
) : BaseViewModel() {
    private val _articles = MutableLiveData<List<ArticleItem>>(mutableListOf())
    val articles: MutableLiveData<List<ArticleItem>> = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var page = 0
    val pageSize = 10

    private val _error = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = 0
    )
    val error = _error.asSharedFlow()

    private val _goToDetail = MutableSharedFlow<ArticleDomain>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = 0
    )
    val goToDetail = _goToDetail.asSharedFlow()

    init {
        loadArticles()
    }

    fun loadArticles() {
        articlesInteractor.getFavouriteArticle(offset = page, size = pageSize)
            .loading(_isLoading)
            .subscribeBy(
                onSuccess = {
                    val dop = it.contents?.map { article -> article.toUI() } ?: mutableListOf()
                    val newList =
                        mutableListOf<ArticleItem>().apply {
                            addAll(_articles.value ?: mutableListOf())
                            addAll(dop)
                        }
                    _articles.postValue(newList)
                    page++
                },
                onError = {
                    _error.tryEmit(ErrorHelper.getErrorMessage(it))
                }
            )
            .addTo(disposables)
    }

    fun changeLike(id: Long, goLike: Boolean) {
        articlesInteractor.changeLike(id, goLike)
            .subscribeBy(
                onComplete = {
                    val tmpList = articles.value?.toMutableList() ?: mutableListOf()
                    tmpList.forEachIndexed { index, articleItem ->
                        if (articleItem.id == id) {
                            tmpList[index] = articleItem.copy(
                                isLike = goLike, likesCount = articleItem.likesCount?.plus(
                                    if (goLike) 1 else -1
                                )
                            )
                        }
                    }
                    _articles.postValue(tmpList)
                },
                onError = {
                    _error.tryEmit(ErrorHelper.getErrorMessage(it))
                }
            )
            .addTo(disposables)
    }

    fun changeFavourite(id: Long, goFavourite: Boolean) {
        articlesInteractor.changeFavourite(id, goFavourite)
            .subscribeBy(
                onComplete = {
                    val tmpList = articles.value?.toMutableList() ?: mutableListOf()
                    tmpList.forEachIndexed { index, articleItem ->
                        if (articleItem.id == id) {
                            tmpList[index] = articleItem.copy(isSaveToFavourite = goFavourite)
                        }
                    }
                    _articles.postValue(tmpList)
                },
                onError = {
                    _error.tryEmit(ErrorHelper.getErrorMessage(it))
                }
            )
            .addTo(disposables)
    }

    fun getDetailArticle(id: Long) {
        articlesInteractor.getDetailArticle(id)
            .subscribeBy(
                onSuccess = {
                    _goToDetail.tryEmit(it)
                },
                onError = {
                    _error.tryEmit(ErrorHelper.getErrorMessage(it))
                }
            )
            .addTo(disposables)
    }
}