package com.example.bok.ui.book

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.example.bok.data.database.Book
import com.example.bok.data.enum_classes.Genre
import com.example.bok.data.enum_classes.Language
import com.example.bok.data.enum_classes.ReadingStatus
import com.example.bok.data.repository.BookRepository
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date


// https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#8

class BookEntryViewModel(private val bookRepository: BookRepository): ViewModel(){
    var bookUiState by mutableStateOf(BookUiState())


    fun updateUiState(bookInfo: BookInfo){
        bookUiState =
            BookUiState(bookInfo = bookInfo, isBookEntryValid = validateInput(bookInfo))
    }

    suspend fun resetUiState(){
        bookUiState = BookUiState()

    }

    suspend fun saveBook(){
        if (validateInput()){
            bookRepository.insertBook((bookUiState.bookInfo.toBook()))
        }
    }

    private fun validateInput(uiState: BookInfo = bookUiState.bookInfo): Boolean {
        return with(uiState){
            title.isNotBlank() &&
                    isbn.isNotBlank() &&
                    authors.isNotBlank() &&
                    numPages >= 0 &&
                    numPages <= 3000 &&
                    isbn.isNotBlank() &&
                    pubYear >= 1800 //Probably have to do something about this and numPages
                    // Usikker på haveRead, language, genre or possess
        }
    }
}

data class BookUiState(
    val bookInfo: BookInfo = BookInfo(),
    val isBookEntryValid: Boolean = false // Not sure what this does
)

// I am not entirely sure about what all of these values should be
data class BookInfo(
    val title: String = "",
    val authors: String = "",
    val numPages: Int = 0,
    val language: Language = Language.NORSK,
    val isbn: String = "",
    val genre: Genre = Genre.IKKE_DEFINERT,
    val pubYear: Int = Calendar.getInstance().get(Calendar.YEAR), // CHATGPT
    val readingStatus: ReadingStatus = ReadingStatus.ULEST,
    val doPossess: Boolean = false
)

// Convert BookInfo to Book
// Maybe some of the parameters should be set to something if they are not valid?
fun BookInfo.toBook(): Book = Book(
    title = title,
    authors = authors,
    numberOfPages = numPages,
    language = language,
    isbn = isbn,
    genre = genre,
    pubYear = pubYear,
    doPossess = doPossess,
    readingStatus = readingStatus
)


//Convert Book to BookInfoUiState
fun Book.toBookUiState(isBookEntryValid: Boolean = false): BookUiState =
    BookUiState(
        bookInfo = this.toBookInfo(),
        isBookEntryValid = isBookEntryValid
    )


// Convert Book to BookInfo
// Not sure about the toString()-conversions?
fun Book.toBookInfo(): BookInfo = BookInfo(
    title = title,
    authors = authors,
    numPages = numberOfPages,
    language = language,
    isbn = isbn,
    genre = genre,
    pubYear = pubYear,
    doPossess = doPossess,
    readingStatus = readingStatus

)