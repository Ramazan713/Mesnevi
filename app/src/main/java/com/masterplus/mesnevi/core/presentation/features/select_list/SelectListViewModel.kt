package com.masterplus.mesnevi.core.presentation.features.select_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masterplus.mesnevi.core.domain.preferences.AppPreferences
import com.masterplus.mesnevi.features.list.domain.repo.ListContentsRepo
import com.masterplus.mesnevi.features.list.domain.use_case.list_contents.ListContentsUseCases
import com.masterplus.mesnevi.features.list.domain.use_case.lists.ListUseCases
import com.masterplus.mesnevi.core.presentation.features.select_list.constants.SelectListMenuEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectListViewModel @Inject constructor(
    private val listUseCases: ListUseCases,
    private val listContentsUseCases: ListContentsUseCases,
    private val listContentsRepo: ListContentsRepo,
    private val appPreferences: AppPreferences
): ViewModel(){

    var state by mutableStateOf(SelectListState())
        private set

    private var loadDataJob: Job? = null

    fun onEvent(event: SelectListEvent){
        when(event){
            is SelectListEvent.NewList -> {
                viewModelScope.launch {
                    listUseCases.insertList.invoke(event.listName)
                }
            }
            is SelectListEvent.LoadData -> {
                loadData(event)
            }
            is SelectListEvent.ShowModal -> {
                state = state.copy(
                    showModal = event.showModal,
                    modalEvent = event.modalEvent
                )
            }
            is SelectListEvent.AddToFavorite -> {
                viewModelScope.launch {
                    listContentsUseCases.addFavoriteListContents.invoke(event.contentId)
                }
            }
            is SelectListEvent.AddToList -> {
                viewModelScope.launch {
                    listContentsUseCases.addListContents.invoke(event.listView,event.contentId)
                }
            }
        }
    }

    private fun getListMenuItems(inFavorite: Boolean, isInList: Boolean): List<SelectListMenuEnum>{
        return mutableListOf<SelectListMenuEnum>().apply {
            if(inFavorite) add(SelectListMenuEnum.addedFavorite)
                else add(SelectListMenuEnum.addFavorite)
            if(isInList) add(SelectListMenuEnum.addedList)
                else add(SelectListMenuEnum.addList)
        }.toList()
    }

    private fun loadData(event: SelectListEvent.LoadData){
        loadDataJob?.cancel()
        loadDataJob = viewModelScope.launch {
            val useArchiveAsList = appPreferences.getItem(AppPreferences.useArchiveLikeList)
            val listsFlow = listContentsUseCases.getSelectableLists(useArchiveAsList,event.contentId)
            val hasInFavoriteListFlow = listContentsRepo.hasContentInFavoriteList(event.contentId)
            val hasInRemovableListFlow = listContentsRepo.hasContentInRemovableList(event.contentId)

            combine(listsFlow,hasInFavoriteListFlow,hasInRemovableListFlow){lists,inFavorite,inLists->{
                state.copy(
                    items = lists,
                    isFavorite = inFavorite,
                    isAddedToList = inLists,
                    listMenuItems = getListMenuItems(inFavorite,inLists),
              )
            }}.collectLatest {
                state = it()
            }

        }
    }

}