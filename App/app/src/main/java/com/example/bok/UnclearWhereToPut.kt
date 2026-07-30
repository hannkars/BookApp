package com.example.bok

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bok.data.database.Book
import com.example.bok.data.database.BookDao
import com.example.bok.data.enum_classes.Genre
import com.example.bok.data.enum_classes.Language
import com.example.bok.data.enum_classes.ReadingStatus
import kotlin.math.ceil
import kotlin.math.floor

fun testingDatabase(bookDao: BookDao){
    val book1 = Book(
        title = "1984",
        authors = "George Orwell",
        pubYear = 1949,
        numberOfPages = 361,
        language = Language.NORSK,
        isbn = "9788205508163",
        genre = Genre.SCIENCE_FICTION,
        readingStatus = ReadingStatus.ULEST,
        doPossess = true,
    )
    val book2 = Book(
        title = "Et lite liv",
        authors = "Hanya Yanagihara",
        numberOfPages = 795,
        pubYear = 2017,
        language = Language.NORSK,
        isbn = "9788205498761",
        genre = Genre.TRAGEDIE,
        readingStatus = ReadingStatus.LEST,
        doPossess = true)

    bookDao.insertBook(book1)
    bookDao.insertBook(book2)
}

@Composable
fun DatabaseOutput(books: List<Book>, modifier: Modifier = Modifier) {
    Column() {
        for (book in books){
            Text(
                text = "Tittel: ${book.title}",
                modifier = modifier
            )

        }
    }
}


fun daysToComplete(readingSpeed: Int, pages: Int, minutesPerDay: Int): Int {
    // readingSpeed is in seconds/page, which is how many seconds on average one uses to read one page
    val secondsToCompleteBook: Int = readingSpeed * pages
    val minutesToCompleteBook: Int = secondsToCompleteBook / 60
    val daysToComplete = (minutesToCompleteBook / minutesPerDay.toDouble())
    return ceil(daysToComplete).toInt()
}

fun minutesPerDayToCompleteInDays(readingSpeed:Int, pages: Int, days: Int): Int{
    val secondsToCompleteBook: Int = readingSpeed * pages
    val minutesToCompleteBook: Int = secondsToCompleteBook / 60
    val minutesPerDay: Double = minutesToCompleteBook.toDouble()/days
    return minutesPerDay.toInt()
}

fun minutesToHours(minutes: Int): String{
    val wholeHours = floor(minutes.toFloat()/60).toInt()
    val minutes = minutes % 60
    return "$wholeHours, $minutes"
}