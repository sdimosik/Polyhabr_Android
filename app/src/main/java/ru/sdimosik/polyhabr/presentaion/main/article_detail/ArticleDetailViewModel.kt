package ru.sdimosik.polyhabr.presentaion.main.article_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.sdimosik.polyhabr.common.ui.BaseViewModel
import ru.sdimosik.polyhabr.data.network.model.comment.CommentGetParam
import ru.sdimosik.polyhabr.domain.interactor.IArticlesInteractor
import ru.sdimosik.polyhabr.domain.interactor.ICommentInteractor
import ru.sdimosik.polyhabr.domain.model.toUI
import ru.sdimosik.polyhabr.presentaion.main.article_detail.adapter.CommentItem
import ru.sdimosik.polyhabr.utils.ErrorHelper
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val commentInteractor: ICommentInteractor,
    private val articlesInteractor: IArticlesInteractor
) : BaseViewModel() {

    private val _comments = MutableLiveData<List<CommentItem>>(
        // TODO
        mutableListOf(
            CommentItem(
                id = 1,
                text = "text",
                articleId = 0,
                userId = 0,
                date = "date",
                login = "login",
            ),
            CommentItem(
                id = 2,
                text = "text",
                articleId = 0,
                userId = 0,
                date = "date",
                login = "login",
            ),
            CommentItem(
                id = 3,
                text = "text",
                articleId = 0,
                userId = 0,
                date = "date",
                login = "login",
            ),
        )
    )
    val comments: MutableLiveData<List<CommentItem>> = _comments

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLiked = MutableLiveData<Boolean>()
    val isLiked: LiveData<Boolean> = _isLiked

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _likeCount = MutableLiveData<Int?>()
    val likeCount: LiveData<Int?> = _likeCount

    private val _error = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = 0
    )
    val error = _error.asSharedFlow()

    private var page = 0
    val pageSize = 10

    fun likeCountSave(viewCount: Int?) {
        _likeCount.postValue(viewCount)
    }

    fun loadComments(id: Long) {
        val param = CommentGetParam(
            offset = page,
            size = pageSize,
            articleId = id,
        )
        commentInteractor.getCommentsByArticleId(param)
            .subscribeBy(
                onSuccess = {
                    val dop = it.contents.map { article -> article.toUI() }
                    val newList =
                        mutableListOf<CommentItem>().apply {
                            addAll(_comments.value ?: mutableListOf())
                            addAll(dop)
                        }
                    _comments.postValue(newList)
                    page++
                },
                onError = {
                    _error.tryEmit(ErrorHelper.getErrorMessage(it))
                }
            )
            .addTo(disposables)
    }

    fun createComment(articleId: Long, text: String) {
        commentInteractor.createComment(articleId, text)
            .subscribeBy(
                onComplete = {
                    loadComments(articleId)
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
                    _isLiked.postValue(goLike)
                    val oldCount = _likeCount.value
                    oldCount?.let {
                        _likeCount.postValue(
                            if (goLike) {
                                it + 1
                            } else {
                                it - 1
                            }
                        )
                    }
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
                    if (goFavourite) {
                        _isFavorite.postValue(true)
                    } else {
                        _isFavorite.postValue(false)
                    }
                },
                onError = {
                    _error.tryEmit(ErrorHelper.getErrorMessage(it))
                }
            )
            .addTo(disposables)
    }
}
