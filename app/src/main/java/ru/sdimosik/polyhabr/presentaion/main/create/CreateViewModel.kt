package ru.sdimosik.polyhabr.presentaion.main.create

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.zipWith
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.sdimosik.polyhabr.common.ui.BaseViewModel
import ru.sdimosik.polyhabr.data.network.model.article.ArticleRequest
import ru.sdimosik.polyhabr.domain.interactor.IArticlesInteractor
import ru.sdimosik.polyhabr.domain.interactor.IFileInteractor
import ru.sdimosik.polyhabr.utils.ErrorHelper
import ru.sdimosik.polyhabr.utils.loading
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val articlesInteractor: IArticlesInteractor,
    private val fileInteractor: IFileInteractor
) : BaseViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _disciplines = MutableLiveData<List<String>>()
    val disciplines: LiveData<List<String>> = _disciplines

    private val _articleType = MutableLiveData<List<String>>()
    val articleType: LiveData<List<String>> = _articleType

    private val _pdfUri = MutableLiveData<Uri?>()
    val pdfUri: LiveData<Uri?> = _pdfUri

    private val _error = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = 0
    )
    val error = _error.asSharedFlow()

    private val _showSuccess = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = 0
    )
    val showSuccess = _showSuccess.asSharedFlow()

    private var articleArticleType = ""

    private var discipline = ""

    var title = ""
    var previewText = ""
    var text = ""


    init {
        loadBasic()
    }


    private fun loadBasic() {
        articlesInteractor.getArticleTypes(0, 100)
            .zipWith(articlesInteractor.getDisciplines(0, 100))
            .subscribeBy(
                onSuccess = { pair ->
                    _articleType.postValue(pair.first.contents.map { it.name })
                    _disciplines.postValue(pair.second.contents.map { it.name })
                },
                onError = {
                    _error.tryEmit(ErrorHelper.getErrorMessage(it))
                }
            )
            .addTo(disposables)
    }

    fun createArticle() {
        val articleRequest = ArticleRequest(
            title = title,
            text = text,
            previewText = previewText,
            articleType = articleArticleType,
            listDisciplineName = listOf(discipline),
            listTag = listOf(),
        )
        articlesInteractor.createArticle(articleRequest)
            .flatMapCompletable {
                if (it.isSuccess) {
                    _showSuccess.tryEmit(Unit)
                    _pdfUri.value?.let { uri ->
                        fileInteractor.savePdfToArticle(it.id!!, uri.toString())
                    } ?: Completable.error(RuntimeException("Возникла ошибка с файлом"))
                } else {
                    Completable.error(RuntimeException("Возникла ошибка при загрузке статьи"))
                }
            }
            .loading<Boolean>(_isLoading)
            .subscribeBy(
                onComplete = {
                    _showSuccess.tryEmit(Unit)
                },
                onError = {
                    _error.tryEmit(ErrorHelper.getErrorMessage(it))
                }
            )
            .addTo(disposables)
    }

    fun savePdf(uri: Uri?) {
        _pdfUri.postValue(uri)
    }

    fun deletePdf() {
        _pdfUri.postValue(null)
    }

    fun storeArticleType(it: String) {
        articleArticleType = it
    }

    fun storeDiscipline(it: String) {
        discipline = it
    }

    fun clearFields() {
        deletePdf()
        title = ""
        previewText = ""
        text = ""
    }
}