package com.example.captionai.data.repository

import android.net.Uri
import com.example.captionai.domain.model.User
import com.example.captionai.domain.repository.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ProfileRepository {

    override fun getUserProfile(): Flow<User?> = callbackFlow {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            trySend(null)
            close()
            return@callbackFlow
        }

        val listener = firestore.collection("users").document(userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val user = snapshot?.toObject(User::class.java)
                trySend(user)
            }

        awaitClose { listener.remove() }
    }

    override suspend fun updateUserProfile(name: String, handle: String) {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("users").document(userId)
            .update(mapOf("name" to name, "handle" to handle))
            .await()
    }

    override suspend fun uploadProfileImage(uri: Uri): String {
        val userId = auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")
        val ref = storage.reference.child("profile_images/$userId.jpg")
        ref.putFile(uri).await()
        val downloadUrl = ref.downloadUrl.await().toString()
        
        firestore.collection("users").document(userId)
            .update("profileImageUrl", downloadUrl)
            .await()
            
        return downloadUrl
    }

    override suspend fun logout() {
        auth.signOut()
    }
}
