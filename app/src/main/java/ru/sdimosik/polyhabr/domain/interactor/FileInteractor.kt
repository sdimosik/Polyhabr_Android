package ru.sdimosik.polyhabr.domain.interactor

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import ru.sdimosik.polyhabr.data.file.ReadFileResult
import ru.sdimosik.polyhabr.data.repository_impl.NetworkRepository
import ru.sdimosik.polyhabr.domain.repository.INetworkRepository
import javax.inject.Inject

class FileInteractor @Inject constructor(
    private val networkRepository: INetworkRepository,
    private val context: Context
) : IFileInteractor {
    override fun savePdfToArticle(
        articleId: Long,
        uriFile: String
    ): Completable {
        val readFile = tryReadFile(context, uriFile)
        return networkRepository.savePdfToArticle(articleId, readFile)
    }

    override fun downFile(docId: String): Single<ResponseBody> {
        return networkRepository.downFile(docId)
    }

    private fun tryReadFile(context: Context, localUri: String): ReadFileResult {
        val contentResolver = context.contentResolver
        val androidUri = Uri.parse(localUri)
        try {
            contentResolver.query(
                androidUri,
                null,
                null,
                null,
                null
            )!!.use { cursor ->
                if (cursor.count == 0) throw IllegalStateException("File not found")
                cursor.moveToFirst()
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val fileName = cursor.getString(nameIndex)
                val bytes = contentResolver.openInputStream(androidUri)!!.use { inputStream ->
                    inputStream.readBytes()
                }
                return ReadFileResult(fileName, bytes)
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}

interface IFileInteractor {
    fun savePdfToArticle(
        articleId: Long,
        uriFile: String
    ): Completable

    fun downFile(
        docId: String,
    ): Single<ResponseBody>
}