package com.example.bok.data.repository


import com.example.bok.data.database.Book
import kotlinx.coroutines.flow.Flow


//https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#7
interface BookRepository {

    // I think all methods from the DAO should be here?

    fun getAllBooks(): Flow<List<Book>>


    fun getAllBooksInBookshelf(): Flow<List<Book>>


    fun getAllBooksWantingToRead(): Flow<List<Book>>


    fun getAllUnreadBooksInBookself(): Flow<List<Book>>

    fun getAllReadBooksInBookself(): Flow<List<Book>>


    fun getBooksByTitle(title: String): Flow<List<Book>>


    //DELETING



    /*UPDATING*/
    suspend fun updateBookInfoToReadByAuthorsAndTitle(authors: String, title: String)

    suspend fun updateBookInfoToReadByIsbn(isbn: String)

    // You probably need some methods for changing books already added if something is not correct -> Perhaps just retrieve the BookInfo you want to change (filling out all the fields with the current info and allowing the users to change the wanted fields, and then insert the changed BookInfo-object into the database again


    suspend fun insertBook(book: Book)

    suspend fun deleteBook(book:Book)

}