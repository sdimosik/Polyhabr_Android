package ru.sdimosik.polyhabr.data

import ru.sdimosik.polyhabr.data.network.model.article.ArticleListResponse
import ru.sdimosik.polyhabr.data.network.model.article.ArticleResponse
import ru.sdimosik.polyhabr.domain.model.ArticleDomain
import ru.sdimosik.polyhabr.domain.model.ArticleListDomain

fun ArticleListResponse.toDomain() =
    ArticleListDomain(
        contents = contents?.map { it.toDomain() },
        totalElements = totalElements,
        totalPages = totalPages,
    )

fun ArticleResponse.toDomain() =
    ArticleDomain(
        id = id ?: -1,
        date = date,
        title = title,
        filePdf = filePdf,
        likes = likes,
        previewText = previewText,
        typeId = typeId,
        user = user,
        text = text,
        listDisciplineName = listDisciplineName,
        listTag = listTag,
        fileId = fileId,
        viewCount = viewCount,
        isSaveToFavourite = isSaveToFavourite,
        pdfId = pdfId,
        previewImgId = previewImgId,
    )