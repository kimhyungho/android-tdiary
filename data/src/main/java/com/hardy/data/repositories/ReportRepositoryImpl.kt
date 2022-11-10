package com.hardy.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hardy.domain.model.Report
import com.hardy.domain.model.Response
import com.hardy.domain.repositories.ReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ReportRepository {
    private val uid get() = firebaseAuth.currentUser?.uid

    override fun reportPost(postId: String, title: String, content: String): Flow<Response<Unit>> =
        flow {
            try {
                emit(Response.Loading)
                val report = Report(
                    reporterUid = uid ?: throw IllegalArgumentException("not sign in"),
                    postId = postId,
                    title = title,
                    content = content,
                    Date()
                )
                firestore.collection("report").add(report).await()
                emit(Response.Success(Unit))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        }
}