package com.masterplus.mesnevi.features.books.presentation.detail_book.model

import com.masterplus.mesnevi.core.domain.model.ContentNotes
import com.masterplus.mesnevi.features.books.domain.model.TitleContentNotes
import com.masterplus.mesnevi.features.books.domain.model.TitleNotes

data class TitleContentDetailModel(
    val contentNote: ContentNotes,
    val titleNotes: TitleNotes?
){
    companion object{
        fun fromTitleContentNotesList(titleContents: List<TitleContentNotes>): List<TitleContentDetailModel>{
            val items = mutableListOf<TitleContentDetailModel>()
            for(titleContent in titleContents){
                val title = titleContent.titleNotes
                val contents = titleContent.contentNotes
                if (contents.isEmpty())continue
                items.add(TitleContentDetailModel(contents.first(),title))
                for(content in contents.subList(1,contents.size)){
                    items.add(TitleContentDetailModel(content,null))
                }
            }
            return items
        }
    }
}
