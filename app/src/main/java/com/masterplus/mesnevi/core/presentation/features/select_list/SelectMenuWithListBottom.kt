package com.masterplus.mesnevi.core.presentation.features.select_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.masterplus.mesnevi.core.domain.model.ContentModel
import com.masterplus.mesnevi.core.domain.constants.IMenuItemEnum
import com.masterplus.mesnevi.core.presentation.components.CustomModalBottomSheet
import com.masterplus.mesnevi.core.presentation.dialog_body.SelectMenuItemBottomContent
import com.masterplus.mesnevi.core.presentation.features.select_list.constants.SelectListMenuEnum

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun SelectMenuWithListBottom(
    onDismiss: ()->Unit,
    items: List<IMenuItemEnum>,
    contentModel: ContentModel,
    onClickItem:(IMenuItemEnum?)->Unit,
    title: String? = null,
    listViewModel: SelectListViewModel = hiltViewModel()
){

    val state = listViewModel.state
    val newItems = remember {
        mutableStateOf(emptyList<IMenuItemEnum>())
    }

    LaunchedEffect(contentModel){
        listViewModel.onEvent(SelectListEvent.LoadData(contentModel.id ?: 0))
    }

    LaunchedEffect(items,state.listMenuItems){
        newItems.value = mutableListOf<IMenuItemEnum>().apply {
            addAll(state.listMenuItems)
            addAll(items)
        }
    }

    SelectMenuItemBottomContent(
        items = newItems.value,
        title = title,
        onClickItem = {menuItem->
            when(menuItem){
                SelectListMenuEnum.addList->{
                    listViewModel.onEvent(
                        SelectListEvent.ShowModal(
                            true,
                            SelectListModalEvent.ShowSelectLists
                        )
                    )
                }
                SelectListMenuEnum.addedList->{
                    listViewModel.onEvent(
                        SelectListEvent.ShowModal(
                            true,
                            SelectListModalEvent.ShowSelectLists
                        )
                    )
                }
                SelectListMenuEnum.addFavorite->{
                    listViewModel.onEvent(SelectListEvent.AddToFavorite(contentModel.id ?: 0))
                }
                SelectListMenuEnum.addedFavorite->{
                    listViewModel.onEvent(SelectListEvent.AddToFavorite(contentModel.id ?: 0))
                }
                else->{
                    onClickItem(menuItem)
                }
            }
        }
    )


    if(state.showModal){
        CustomModalBottomSheet(
            onDismissRequest = {
                listViewModel.onEvent(SelectListEvent.ShowModal(false))
            },
        ){
            SelectListBottomContent(
                contentId = contentModel.id?:0,
                items = state.items,
                onEvent = {listViewModel.onEvent(it)}
            )
        }
    }



}