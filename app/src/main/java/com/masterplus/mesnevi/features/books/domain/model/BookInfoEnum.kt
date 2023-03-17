package com.masterplus.mesnevi.features.books.domain.model

import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.util.UiText

enum class BookInfoEnum(val bookId: Int) {
    book1(1),
    book2(2),
    book3(3),
    book4(4),
    book5(5),
    book6(6);

    val title: UiText get() {
        return UiText.Resource(R.string.n_notebook, listOf(this.bookId.toString()))
    }

    companion object{
        fun fromBookId(bookId: Int): BookInfoEnum{
            return when(bookId){
                1 -> book1
                2 -> book2
                3 -> book3
                4 -> book4
                5 -> book5
                6 -> book6
                else -> book1
            }
        }
    }
}