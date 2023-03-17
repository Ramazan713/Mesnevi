package com.masterplus.mesnevi.features.settings.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.preferences.AppPreferences
import com.masterplus.mesnevi.core.domain.repo.ThemeRepo
import com.masterplus.mesnevi.core.domain.util.Resource
import com.masterplus.mesnevi.core.domain.util.UiEvent
import com.masterplus.mesnevi.core.domain.util.UiText
import com.masterplus.mesnevi.features.settings.domain.manager.AuthManager
import com.masterplus.mesnevi.features.settings.domain.manager.BackupManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val themeRepo: ThemeRepo,
    private val appPreferences: AppPreferences,
    private val authManager: AuthManager,
    private val backupManager: BackupManager
): ViewModel(){

    var state by mutableStateOf(SettingState())
        private set

    private var _settingUiEvent = Channel<SettingUiEvent>()
    val settingUiEvent = _settingUiEvent.receiveAsFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        init()
        listenUser()
    }

    private var userListenerJob: Job? = null

    fun onEvent(event: SettingEvent){
        when(event){
            is SettingEvent.SetDynamicTheme -> {
                val updatedModel = state.themeModel.copy(useDynamicColor = event.useDynamic)
                themeRepo.updateThemeModel(updatedModel)
                state = state.copy(themeModel = updatedModel)
            }
            is SettingEvent.SetThemeEnum -> {
                val updatedModel = state.themeModel.copy(themeEnum = event.themeEnum)
                themeRepo.updateThemeModel(updatedModel)
                state = state.copy(themeModel = updatedModel)
            }
            is SettingEvent.ShowDialog -> {
                state = state.copy(
                    showDialog = event.showDialog,
                    dialogEvent = event.dialogEvent
                )
            }
            is SettingEvent.SetSearchCriteria -> {
                appPreferences.setEnumItem(AppPreferences.searchCriteria,event.criteria)
                state = state.copy(
                    searchCriteria = event.criteria
                )
            }
            is SettingEvent.ResetDefaultValues -> {
                appPreferences.clear()
                init()
                themeRepo.updateThemeModel(state.themeModel)
            }
            is SettingEvent.UseArchiveAsList -> {
                appPreferences.setItem(AppPreferences.useArchiveLikeList,event.useArchiveAsList)
                state = state.copy(
                    useArchiveAsList = event.useArchiveAsList
                )
            }
            is SettingEvent.SetFontSize -> {
                appPreferences.setEnumItem(AppPreferences.fontSizeEnum,event.fontSize)
                state = state.copy(
                    fontSize = event.fontSize
                )
            }
            is SettingEvent.ShowModal -> {
                state = state.copy(
                    showModal = event.showModal,
                    modalEvent = event.modalEvent
                )
            }
            is SettingEvent.LoadData -> {
                init()
            }
            is SettingEvent.SignInLaunch -> {
                viewModelScope.launch {
                    _settingUiEvent.send(
                        SettingUiEvent.LaunchGoogleSignIn(authManager.getGoogleSignInIntent())
                    )
                }
            }
            is SettingEvent.SignOut -> {
                viewModelScope.launch {
                    state = state.copy(isLoading = true)
                    when(val result = authManager.signOut(event.backupBeforeSignOut)){
                        is Resource.Success->{
                            showMessage(UiText.Resource(R.string.successfully_log_out))
                        }
                        is Resource.Error->{
                            showMessage(result.error)
                        }
                    }
                    state = state.copy(isLoading = false)
                }
            }
            is SettingEvent.SignInWithGoogle -> {
                signIn(event)
            }
            is SettingEvent.LoadLastBackup -> {
                loadLastBackup()
            }
            is SettingEvent.NotShowBackupInitDialog -> {
                appPreferences.setItem(AppPreferences.showBackupSectionForLogin,false)
            }
            is SettingEvent.DeleteAllUserData -> {
               viewModelScope.launch {
                   backupManager.deleteAllLocalUserData(false)
                   showMessage(UiText.Resource(R.string.successfully_deleted))
               }
            }
        }
    }

    private fun loadLastBackup(){
        val user = authManager.currentUser() ?: return
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            when(val result = backupManager.downloadLastBackup(user)){
                is Resource.Error -> {
                    showMessage(result.error)
                }
                is Resource.Success -> {
                    showMessage(UiText.Resource(R.string.success))
                    _settingUiEvent.send(SettingUiEvent.RefreshApp)
                }
            }
            state = state.copy(isLoading = false)
        }
    }


    private fun signIn(event: SettingEvent.SignInWithGoogle){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            when(val result = authManager.signInWithGoogle(event.activityResult)){
                is Resource.Success->{
                    _uiEvent.send(UiEvent.ShowMessage(UiText.Resource(R.string.successfully_log_in)))
                    state = state.copy(isLoading = false)
                    val hasBackupMetas = authManager.hasBackupMetas()
                    if(hasBackupMetas && appPreferences.getItem(AppPreferences.showBackupSectionForLogin)){
                        state = state.copy(
                            showModal = true,
                            modalEvent = SettingModalEvent.BackupSectionInit,
                        )
                    }
                }
                is Resource.Error->{
                    state = state.copy(isLoading = false)
                    _uiEvent.send(UiEvent.ShowMessage(result.error))
                }
            }

        }
    }

    private fun init(){
        val fontSize = appPreferences.getEnumItem(AppPreferences.fontSizeEnum)
        val themeModel = themeRepo.getThemeModel()
        val searchCriteria = appPreferences.getEnumItem(AppPreferences.searchCriteria)
        val useArchiveAsList = appPreferences.getItem(AppPreferences.useArchiveLikeList)
        state = state.copy(
            themeModel = themeModel,
            searchCriteria = searchCriteria,
            useArchiveAsList = useArchiveAsList,
            fontSize = fontSize
        )
    }

    private fun listenUser(){
        userListenerJob?.cancel()
        userListenerJob = viewModelScope.launch {
            authManager.userFlow().collectLatest { user->
                state = state.copy(
                    user = user
                )
            }
        }
    }

    private suspend fun showMessage(uiText: UiText){
        _uiEvent.send(UiEvent.ShowMessage(uiText))
    }


}



















