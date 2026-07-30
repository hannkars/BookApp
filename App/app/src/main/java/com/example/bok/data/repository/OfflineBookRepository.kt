package com.example.bok.data.repository

import com.example.bok.data.database.Book
import com.example.bok.data.database.BookDao
import kotlinx.coroutines.flow.Flow

//https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#7

class OfflineBookRepository (private val bookDao: BookDao): BookRepository{
    //Remember to override every method from BookRepository

    override fun getAllBooks(): Flow<List<Book>> = bookDao.getAllBooks()

    override suspend fun insertBook(book: Book) = bookDao.insertBook(book)

    override fun getAllBooksInBookshelf(): Flow<List<Book>> = bookDao.getAllBooksInBookshelf()

    override fun getAllBooksWantingToRead(): Flow<List<Book>> = bookDao.getAllReadBooksInBookself()

    override fun getAllUnreadBooksInBookself(): Flow<List<Book>> = bookDao.getAllUnreadBooksInBookself()

    override fun getAllReadBooksInBookself(): Flow<List<Book>> = bookDao.getAllReadBooksInBookself()

    override fun getBooksByTitle(title: String): Flow<List<Book>> = bookDao.getBooksByTitle(title)




    /*UPDATING*/
    override suspend fun updateBookInfoToReadByAuthorsAndTitle(authors: String, title: String) = bookDao.updateBookInfoToReadByAuthorsAndTitle(authors, title)

    override suspend fun updateBookInfoToReadByIsbn(isbn: String) = bookDao.updateBookInfoToReadByIsbn(isbn)

    // You probably need some methods for changing books already added if something is not correct -> Perhaps just retrieve the BookInfo you want to change (filling out all the fields with the current info and allowing the users to change the wanted fields, and then insert the changed BookInfo-object into the database again

    override suspend fun deleteBook(book: Book) = bookDao.deleteBook(book)

}