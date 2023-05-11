package ru.sdimosik.polyhabr.data.network.model.article

import com.google.gson.annotations.SerializedName
import ru.sdimosik.polyhabr.data.network.model.user.UserOtherResponse

data class ArticleResponse(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("filePdf")
    val filePdf: String? = null,
    @SerializedName("likes")
    val likes: Int?,
    @SerializedName("previewText")
    val previewText: String?,
    @SerializedName("typeId")
    val typeId: ArticleType?,
    @SerializedName("user")
    val user: UserOtherResponse? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("listDisciplineName")
    val listDisciplineName: List<String>?,
    @SerializedName("listTag")
    val listTag: List<String>,
    @SerializedName("fileId")
    val fileId: String? = null,
    @SerializedName("viewCount")
    val viewCount: Long?,
    @SerializedName("isSaveToFavourite")
    val isSaveToFavourite: Boolean?,
    @SerializedName("pdfId")
    val pdfId: String?,
    @SerializedName("previewImgId")
    val previewImgId: String?,
    @SerializedName("isLiked")
    val isLiked: Boolean?
)