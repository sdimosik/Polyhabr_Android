package ru.sdimosik.polyhabr.presentaion.main.article_detail

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.ResponseBody
import ru.sdimosik.polyhabr.common.ui.BaseViewModel
import ru.sdimosik.polyhabr.data.network.model.comment.CommentGetParam
import ru.sdimosik.polyhabr.domain.interactor.IArticlesInteractor
import ru.sdimosik.polyhabr.domain.interactor.ICommentInteractor
import ru.sdimosik.polyhabr.domain.interactor.IFileInteractor
import ru.sdimosik.polyhabr.domain.model.toUI
import ru.sdimosik.polyhabr.presentaion.main.article_detail.adapter.CommentItem
import ru.sdimosik.polyhabr.utils.ErrorHelper
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject


@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val commentInteractor: ICommentInteractor,
    private val articlesInteractor: IArticlesInteractor,
    private val fileInteractor: IFileInteractor
) : BaseViewModel() {

    private val _comments = MutableLiveData<List<CommentItem>>()
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

    private val _success = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = 0
    )
    val success = _success.asSharedFlow()

    private var page = 0
    val pageSize = 30

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
                onSuccess = {
                    val newList =
                        mutableListOf<CommentItem>().apply {
                            addAll(_comments.value ?: mutableListOf())
                            add(it.toUI())
                        }
                    _comments.postValue(newList)
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

    fun download(context: Context, pdfId: String, path: String, name: String) {
        fileInteractor.downFile(pdfId)
            .flatMap {
                Single.fromCallable {
                    saveFile(context, it, name)
                }
            }
            .subscribeBy(
                onSuccess = {
                    _success.tryEmit("Файл загружен по пути: $path")
                },
                onError = {
                    _error.tryEmit(ErrorHelper.getErrorMessage(it))
                }
            )
            .addTo(disposables)
    }

    fun saveFile(
        context: Context,
        body: ResponseBody,
        name: String
    ) {
        writeIntoFile(context, name, body.bytes())

    }

    fun writeIntoFile(context: Context, fileName: String, content: ByteArray) {
        var imageOutStream: OutputStream
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues()
            values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName)
            values.put(MediaStore.Files.FileColumns.MIME_TYPE, "application/pdf")
            values.put(MediaStore.Files.FileColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            val uri =
                context.contentResolver
                    .insert(MediaStore.Files.getContentUri("external"), values)!!
            imageOutStream = context.contentResolver.openOutputStream(uri)!!
        } else {
            val imagePath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString()
            val image = File(imagePath, fileName)
            imageOutStream = FileOutputStream(image)
        }

        ByteArrayInputStream(content).use { input ->
            imageOutStream.use { output ->
                input.copyTo(output)
            }
        }
    }
}
