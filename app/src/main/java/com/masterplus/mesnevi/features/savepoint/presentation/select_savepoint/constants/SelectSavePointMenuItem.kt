package com.masterplus.mesnevi.features.savepoint.presentation.select_savepoint.constants

import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.IMenuItemEnum
import com.masterplus.mesnevi.core.domain.constants.IconInfo
import com.masterplus.mesnevi.core.domain.constants.SavePointType
import com.masterplus.mesnevi.core.domain.util.UiText

enum class SelectSavePointMenuItem(val typeId: Int): IMenuItemEnum {
    all(typeId = 0) {
        override val title: UiText
            get() = UiText.Resource(R.string.all)

        override val iconInfo: IconInfo?
            get() = null
    },
    book(typeId = SavePointType.Book.typeId) {
        override val title: UiText
            get() = UiText.Resource(R.string.notebook)
        override val iconInfo: IconInfo?
            get() = null
    },
    search(SavePointType.Search.typeId) {
        override val title: UiText
            get() = UiText.Resource(R.string.search)
        override val iconInfo: IconInfo?
            get() = null
    },
    list(SavePointType.List.typeId) {
        override val title: UiText
            get() = UiText.Resource(R.string.list)
        override val iconInfo: IconInfo?
            get() = null
    };

    companion object{
        fun fromTypeId(typeId: Int): SelectSavePointMenuItem {
            return when(typeId){
                book.typeId-> book
                search.typeId -> search
                search.typeId -> search
                else -> book
            }
        }

        fun fromTypeIds(typeIds: List<Int>, addAll: Boolean = false): List<SelectSavePointMenuItem>{
            val result = mutableListOf<SelectSavePointMenuItem>()
            if(addAll && typeIds.size > 1) result.add(all)
            result.addAll(typeIds.map { fromTypeId(it) })
            return result
        }

    }

}