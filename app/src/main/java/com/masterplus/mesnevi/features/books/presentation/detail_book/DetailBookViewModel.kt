package com.masterplus.mesnevi.features.books.presentation.detail_book

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masterplus.mesnevi.core.domain.preferences.AppPreferences
import com.masterplus.mesnevi.core.domain.repo.ContentRepo
import com.masterplus.mesnevi.features.books.domain.repo.TitleContentsNoteRepo
import com.masterplus.mesnevi.features.books.domain.model.BookInfoEnum
import com.masterplus.mesnevi.features.books.domain.repo.BookRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DetailBookViewModel @Inject constructor(
    private val titleContentsRepo: TitleContentsNoteRepo,
    private val contentRepo: ContentRepo,
    private val bookRepo: BookRepo,
    private val appPreferences: AppPreferences,
): ViewModel() {

    var state by mutableStateOf(DetailBookState())
        private set

    private var _bookUiEvent = Channel<DetailBookUiEvent>()
    val bookUiEvent = _bookUiEvent.receiveAsFlow()


    fun onEvent(event: DetailBookEvent){

        when(event){
            is DetailBookEvent.Init -> {
                loadData(event.bookId,event.pos)
            }
            is DetailBookEvent.ShowDialog -> {
                state = state.copy(
                    showDialog = event.showDialog,
                    dialogEvent = event.dialogEvent
                )
            }
            is DetailBookEvent.ShowModal ->{
                state = state.copy(
                    showModal = event.showModal,
                    modalEvent = event.modalEvent
                )
            }
            is DetailBookEvent.Share -> {
                viewModelScope.launch {
                    contentRepo.getBookIdFromContentId(event.contentModel.id?:0)?.let { bookId->
                        _bookUiEvent.send(DetailBookUiEvent.ShareText(
                            event.contentModel,bookId, event.shareType, event.pos
                        ))
                    }
                }
            }
            is DetailBookEvent.SetFontSize -> {
                appPreferences.setEnumItem(AppPreferences.fontSizeEnum,event.fontSize)
                state = state.copy(
                    fontSize = event.fontSize
                )
            }
            is DetailBookEvent.CopyClipboard -> {
                viewModelScope.launch {
                    contentRepo.getBookIdFromContentId(event.contentModel.id?:0)?.let { bookId->
                        _bookUiEvent.send(DetailBookUiEvent.CopyClipboard(
                            event.contentModel,bookId
                        ))
                    }
                }
            }
        }
    }

    private fun loadData(bookId: Int,scrollPos: Int){
        viewModelScope.launch {
            val fontSize = appPreferences.getEnumItem(AppPreferences.fontSizeEnum)
            state = state.copy(bookInfoEnum = BookInfoEnum.fromBookId(bookId),
                isLoading = true, fontSize = fontSize)

            val book = bookRepo.getBookById(bookId)
            val items = titleContentsRepo.getTitleContentNotesByBookId(bookId)

            state = state.copy(book = book, contents = items,isLoading = false)

            delay(300)
            _bookUiEvent.send(DetailBookUiEvent.ScrollTo(scrollPos))
        }
    }

}