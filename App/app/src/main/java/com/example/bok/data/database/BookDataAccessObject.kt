package com.example.bok.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao{
    //It is possible that you should use Flow as return instead of List, such as Flow<List<Book>>

    // Get all the books in the bookshelf, aka all books where **possess** is True
    @Query("SELECT * FROM book WHERE possess is true")
    fun getAllBooksInBookshelf(): Flow<List<Book>>

    // Get all books they want to read, aka all boks where **posess** is False
    @Query("SELECT * FROM book WHERE possess is false")
    fun getAllBooksWantingToRead(): Flow<List<Book>>

    // Get all books in bookshelf that have **not** been read
    @Query("SELECT * FROM book WHERE possess is true and reading_status = 'ULEST'")
    fun getAllUnreadBooksInBookself(): Flow<List<Book>>

    // Get all books in bookshelf that have been read
    @Query("SELECT * FROM book WHERE possess is true and reading_status = 'LEST' ")
    fun getAllReadBooksInBookself(): Flow<List<Book>>


    //
    // NB! Do I want all books or just the ones I have in bookshelf?
    @Query("SELECT * from book WHERE (:title) is title")
    fun getBooksByTitle(title: String): Flow<List<Book>>

    @Query("SELECT * from book")
    fun getAllBooks(): Flow<List<Book>>



    /*UPDATING*/
    @Query("UPDATE Book SET reading_status = 'LEST' WHERE authors = (:authors) and title = (:title)")
    fun updateBookInfoToReadByAuthorsAndTitle(authors: String, title: String)

    @Query("UPDATE Book SET reading_status = 'LEST' WHERE isbn =(:isbn)")
    fun updateBookInfoToReadByIsbn(isbn: String)

    // You probably need some methods for changing books already added if something is not correct -> Perhaps just retrieve the BookInfo you want to change (filling out all the fields with the current info and allowing the users to change the wanted fields, and then insert the changed BookInfo-object into the database again




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(vararg book: Book) //What does vararg mean?

    @Delete
    fun deleteBook(book: Book)
}


