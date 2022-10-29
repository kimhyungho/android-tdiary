package com.hardy.data.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {
//    @Singleton
//    @Provides
//    fun provideFireBaseDataBase(): FirebaseDatabase {
//        return Firebase.database
//    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

//    @Singleton
//    @Provides
//    fun provideFirebaseStorage() = Firebase.storage
}