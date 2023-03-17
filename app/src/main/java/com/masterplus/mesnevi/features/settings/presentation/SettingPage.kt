package com.masterplus.mesnevi.features.settings.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.ThemeEnum
import com.masterplus.mesnevi.core.domain.util.ToastHelper
import com.masterplus.mesnevi.core.domain.util.UiEvent
import com.masterplus.mesnevi.core.presentation.components.buttons.NegativeButton
import com.masterplus.mesnevi.core.presentation.components.buttons.NegativeFilledButton
import com.masterplus.mesnevi.core.presentation.components.buttons.PrimaryButton
import com.masterplus.mesnevi.core.presentation.components.navigation.CustomTopAppBar
import com.masterplus.mesnevi.core.presentation.dialog_body.LoadingDialog
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowColumnBottomContent
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowQuestionDialog
import com.masterplus.mesnevi.core.presentation.dialog_body.ShowSelectRadioItemAlertDialog
import com.masterplus.mesnevi.core.presentation.extensions.refreshApp
import com.masterplus.mesnevi.features.search.domain.model.SearchCriteria
import com.masterplus.mesnevi.features.settings.presentation.sections.*
import kotlinx.coroutines.flow.collectLatest


@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingPage(

    settingViewModel: SettingViewModel = hiltViewModel()
){
    val state = settingViewModel.state
    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current

    val activityResult = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        settingViewModel.onEvent(SettingEvent.SignInWithGoogle(result))
    }

    LaunchedEffect(true){
        settingViewModel.onEvent(SettingEvent.LoadData)
    }
    LaunchedEffect(true){
        settingViewModel.settingUiEvent.collectLatest { event->
            when(event){
                is SettingUiEvent.LaunchGoogleSignIn -> {
                    activityResult.launch(event.intent)
                }
                is SettingUiEvent.RefreshApp -> {
                    context.refreshApp()
                }
            }
        }
    }

    LaunchedEffect(true){
        settingViewModel.uiEvent.collectLatest { event->
            when(event){
                is UiEvent.ShowMessage -> {
                    ToastHelper.showMessage(event.message,context)
                }
            }
        }
    }


    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.settings),
                scrollBehavior = topAppBarScrollBehavior
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ){paddings->
        LazyColumn(
            modifier = Modifier.padding(paddings)
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
        ){

            item {
                ProfileSettingSection(
                    state = settingViewModel.state,
                    onEvent = {settingViewModel.onEvent(it)}
                )
            }
            item {
                GeneralSettingSection(
                    state = settingViewModel.state,
                    onEvent = {settingViewModel.onEvent(it)}
                )
            }
            item {
                if(state.user!=null){
                    CloudBackupSection {
                        settingViewModel.onEvent(it)
                    }
                }
            }
            item {
                AdvancedSettingSection(
                    state = settingViewModel.state,
                    onEvent = {settingViewModel.onEvent(it)}
                )
            }
            item {
                ApplicationSettingSection()
            }
            item {
                if(state.user!=null){
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                        NegativeButton(
                            title = stringResource(R.string.sign_out),
                            onClick = {
                                settingViewModel.onEvent(SettingEvent.ShowDialog(true,
                                SettingDialogEvent.AskSignOut))
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

            }
        }
    }

    if(state.isLoading){
        LoadingDialog()
    } else if(state.showDialog){
        ShowSettingDialog(
            state = settingViewModel.state,
            onEvent = {settingViewModel.onEvent(it)}
        )
    }else if(state.showModal){
        ShowSettingModal(
            state = settingViewModel.state,
            onEvent = {settingViewModel.onEvent(it)}
        )
    }

}




















