package com.masterplus.mesnevi.features.settings.domain.manager

import android.content.Intent
import androidx.activity.result.ActivityResult
import com.masterplus.mesnevi.core.domain.util.Resource
import com.masterplus.mesnevi.core.domain.util.UiText
import com.masterplus.mesnevi.features.settings.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthManager {

    fun getGoogleSignInIntent(): Intent

    suspend fun signInWithGoogle(activityResult: ActivityResult): Resource<User>

    fun userFlow(): Flow<User?>

    suspend fun signOut(makeBackupBeforeSignOut: Boolean): Resource<UiText>

    fun currentUser(): User?

    suspend fun hasBackupMetas(): Boolean
}