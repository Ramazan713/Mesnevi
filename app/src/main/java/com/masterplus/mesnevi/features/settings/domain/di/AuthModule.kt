package com.masterplus.mesnevi.features.settings.domain.di

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.masterplus.mesnevi.BuildConfig
import com.masterplus.mesnevi.features.settings.data.manager.AuthManagerImpl
import com.masterplus.mesnevi.features.settings.data.repo.FirebaseAuthRepo
import com.masterplus.mesnevi.features.settings.domain.manager.AuthManager
import com.masterplus.mesnevi.features.settings.domain.manager.BackupManager
import com.masterplus.mesnevi.features.settings.domain.repo.AuthRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {


    @Provides
    @Singleton
    fun provideGoogleSignInOptions(): GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestId()
            .requestIdToken(BuildConfig.SERVER_CLIENT_ID)
            .build()

    @Provides
    @Singleton
    fun provideGoogleSignInClient(application: Application,gso: GoogleSignInOptions):
            GoogleSignInClient = GoogleSignIn.getClient(application, gso)


    @Provides
    @Singleton
    fun provideAuthRepo(googleSignInClient: GoogleSignInClient): AuthRepo =
        FirebaseAuthRepo(
            googleSignInClient = googleSignInClient,
            firebaseAuth = FirebaseAuth.getInstance()
        )

    @Provides
    @Singleton
    fun provideAuthManager(authRepo: AuthRepo,
                           backupManager: BackupManager
    ): AuthManager =
        AuthManagerImpl(authRepo, backupManager)

}