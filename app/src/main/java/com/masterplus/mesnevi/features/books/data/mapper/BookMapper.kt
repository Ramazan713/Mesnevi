package com.masterplus.mesnevi.features.books.data.mapper

import com.masterplus.mesnevi.features.books.data.entity.BookEntity
import com.masterplus.mesnevi.features.books.domain.model.Book


fun BookEntity.toBook(): Book{
    return Book(
        id = id,
        name= name,
        description = description
    )
}
