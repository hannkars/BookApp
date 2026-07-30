package com.example.bok

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bok.data.database.Book
import com.example.bok.data.database.BookDatabase
import com.example.bok.data.database.BookDao
import com.example.bok.data.enum_classes.Genre
import com.example.bok.data.enum_classes.Language
import com.example.bok.data.enum_classes.ReadingStatus
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class BookTest {
    private lateinit var bookDao: BookDao
    private lateinit var bookDb: BookDatabase

    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()
        bookDb = Room.inMemoryDatabaseBuilder(context, BookDatabase::class.java).allowMainThreadQueries().build()
        bookDao = bookDb.bookDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        bookDb.close()
    }

    private val theMartianBook = Book(
        title = "The Martian",
        authors = "Andy Weir",
        numberOfPages = 300, //DUMMY
        pubYear = 2011,
        language = Language.ENGELSK,
        isbn = "9781785031137",
        genre = Genre.SCIENCE_FICTION,
        readingStatus = ReadingStatus.ULEST,
        doPossess = false)

    private val sapiensBook = Book(
        title = "Sapiens",
        authors = "Yuval Noah Harari",
        numberOfPages = 100, //DUMMY
        pubYear = 2011,
        language = Language.NORSK,
        isbn = "1111111111111", //DUMMY
        genre = Genre.FAKTA,
        readingStatus = ReadingStatus.ULEST,
        doPossess = true)

    private val homoDeusBook = Book(
        title = "Homo Deus",
        authors = "Yuval Noah Harari",
        numberOfPages = 150, // DUMMY
        pubYear = 2018,
        language = Language.NORSK,
        isbn = "22222222", //DUMMY
        genre = Genre.FAKTA,
        readingStatus = ReadingStatus.ULEST,
        doPossess = true)

    private val kabelTVBook = Book(
        title = "Farmor har kabel-tv",
        authors = "Tore Renberg",
        numberOfPages = 90, // DUMMY
        pubYear = 2023,
        language = Language.NORSK,
        isbn = "22222222", //DUMMY
        genre = Genre.FIKSJON,
        readingStatus = ReadingStatus.LEST,
        doPossess = false)

    private suspend fun addOneBookToDb(){
        bookDao.insertBook(kabelTVBook)
    }

    private suspend fun addAllElementsToDb(){
        bookDao.insertBook(kabelTVBook)
        bookDao.insertBook(theMartianBook)
        bookDao.insertBook(sapiensBook)
        bookDao.insertBook(homoDeusBook)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertOneBookIntoDb() = runBlocking{
        addOneBookToDb()
        val allBooks = bookDao.getAllBooks()
        //assertEquals(allBooks.size, 1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertAllBooksIntoDb() = runBlocking{
        addAllElementsToDb()
        val allBooks = bookDao.getAllBooks()
        //assertEquals(allBooks, 4)
    }

    @Test
    @Throws(Exception::class)
    fun daoQuery_getAllBooksInBookshelfFromDb() = runBlocking{
        addAllElementsToDb()

        val booksInBookshelf = bookDao.getAllBooksInBookshelf()
        //assertEquals(booksInBookshelf.size, 2)

    }


    @Test
    @Throws(Exception::class)
    fun daoQuery_getReadBooksFromDb() = runBlocking{
        addAllElementsToDb()

        val readBooks = bookDao.getAllReadBooksInBookself()
        //assertEquals(readBooks.size, 1)
        //assertEquals(readBooks[0], kabelTVBook)
    }

    @Test
    @Throws(Exception::class)
    fun daoQuery_getBooksByTitleFromDb() = runBlocking{
        addAllElementsToDb()

        val searchingForTitle = bookDao.getBooksByTitle("The Martian")
        //assertEquals(searchingForTitle.size, 1)
    }

    @Test
    @Throws(Exception::class)
    fun daoQuery_getBooksWantingToReadFromDb() = runBlocking{
        addAllElementsToDb()


        val wantingToRead = bookDao.getAllBooksWantingToRead()
        //assertEquals(wantingToRead.size, 3)
    }



}