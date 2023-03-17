package com.masterplus.mesnevi.features.books.domain.repo

import com.masterplus.mesnevi.features.books.domain.model.Book

interface BookRepo {
    suspend fun getBooks(): List<Book>
    suspend fun getBookById(id: Int): Book?
}