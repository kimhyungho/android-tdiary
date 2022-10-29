package com.hardy.data.di

import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.hardy.data.BuildConfig
import com.hardy.data.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GoogleAuthModule {
    @Singleton
    @Provides
    fun provideOneTapClient(
        @ApplicationContext context: Context
    ) = Identity.getSignInClient(context)

    @Singleton
    @Provides
    @Named(Constants.SIGN_IN_REQUEST)
    fun provideSignInRequest() = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId("936507950796-rlmanc4gud64mottggfue24fqrtgic45.apps.googleusercontent.com")
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    @Singleton
    @Provides
    @Named(Constants.SIGN_UP_REQUEST)
    fun provideSignUpRequest() = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId("936507950796-rlmanc4gud64mottggfue24fqrtgic45.apps.googleusercontent.com")
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    @Singleton
    @Provides
    fun provideGoogleSignInOptions() =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("936507950796-rlmanc4gud64mottggfue24fqrtgic45.apps.googleusercontent.com")
            .requestEmail()
            .build()

    @Singleton
    @Provides
    fun provideGoogleSignInClient(
        @ApplicationContext app: Context,
        options: GoogleSignInOptions
    ) = GoogleSignIn.getClient(app, options)
}