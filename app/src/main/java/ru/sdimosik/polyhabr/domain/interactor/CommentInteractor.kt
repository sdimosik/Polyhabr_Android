package ru.sdimosik.polyhabr.domain.interactor

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.sdimosik.polyhabr.data.network.model.comment.CommentGetParam
import ru.sdimosik.polyhabr.domain.model.CommentListDomain
import ru.sdimosik.polyhabr.domain.repository.INetworkRepository
import javax.inject.Inject

class CommentInteractor @Inject constructor(
    private val networkRepository: INetworkRepository
) : ICommentInteractor {
    override fun getCommentsByArticleId(commentGetParam: CommentGetParam): Single<CommentListDomain> {
        return networkRepository.getCommentsByArticleId(commentGetParam)
    }

    override fun createComment(id: Long, text: String): Completable {
        return networkRepository.createComment(id, text)
    }
}

interface ICommentInteractor {
    fun getCommentsByArticleId(commentGetParam: CommentGetParam): Single<CommentListDomain>

    fun createComment(id: Long, text: String): Completable
}