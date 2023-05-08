package ru.sdimosik.polyhabr.presentaion.main.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.sdimosik.polyhabr.common.ui.BaseViewModel
import ru.sdimosik.polyhabr.data.network.param.ArticlesParam
import ru.sdimosik.polyhabr.domain.interactor.IArticlesInteractor
import ru.sdimosik.polyhabr.domain.model.ArticleDomain
import ru.sdimosik.polyhabr.utils.loading
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val articlesInteractor: IArticlesInteractor
) : BaseViewModel() {

    private val _articles = MutableLiveData<List<ArticleDomain>?>()
    val articles: MutableLiveData<List<ArticleDomain>?> = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        loadArticles()
    }

    fun loadArticles() {
        val param = ArticlesParam(
            offset = 0,
            size = 10,
        )
        articlesInteractor.getArticles(param)
            .loading(_isLoading)
            .subscribeBy(
                onSuccess = {
                    _articles.postValue(it.contents)
                },
                onError = {
                    _error.postValue(it.message)
                }
            )
            .addTo(disposables)
    }
}