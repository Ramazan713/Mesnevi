package com.masterplus.mesnevi.features.settings.presentation.cloud_backup_settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.util.Resource
import com.masterplus.mesnevi.core.domain.util.UiEvent
import com.masterplus.mesnevi.core.domain.util.UiText
import com.masterplus.mesnevi.features.settings.domain.repo.AuthRepo
import com.masterplus.mesnevi.features.settings.domain.manager.BackupManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CloudBackupViewModel @Inject constructor(
    private val backupManager: BackupManager,
    private val authRepo: AuthRepo
): ViewModel(){

    var state by mutableStateOf(CloudBackupState())
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun makeBackup(){
        viewModelScope.launch {
            authRepo.currentUser()?.let { user->
                state = state.copy(isLoading = true)
                when(val result = backupManager.uploadBackup(user)){
                    is Resource.Error -> {
                        _uiEvent.send(UiEvent.ShowMessage(result.error))
                    }
                    is Resource.Success -> {
                        _uiEvent.send(UiEvent.ShowMessage(UiText.Resource(R.string.success)))
                    }
                }
                state = state.copy(isLoading = false)
            }
        }
    }


}