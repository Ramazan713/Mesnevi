package com.masterplus.mesnevi.features.books.data.repo

import com.masterplus.mesnevi.features.books.data.mapper.toBook
import com.masterplus.mesnevi.features.books.data.services.BookDao
import com.masterplus.mesnevi.features.books.domain.model.Book
import com.masterplus.mesnevi.features.books.domain.repo.BookRepo
import javax.inject.Inject

class BookRepoImpl @Inject constructor(
    private val bookDao: BookDao
): BookRepo {
    override suspend fun getBooks(): List<Book> {
        return bookDao.getBooks().map { it.toBook() }
    }

    override suspend fun getBookById(id: Int): Book? {
        return bookDao.getBookById(id)?.toBook()
    }
}