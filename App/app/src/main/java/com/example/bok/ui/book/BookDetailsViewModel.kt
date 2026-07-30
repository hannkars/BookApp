package com.example.bok.ui.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bok.data.database.Book
import com.example.bok.data.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class BookDetailsViewModel(
    private val bookRepository: BookRepository): ViewModel(){

    // I don't know how to change the method for getting books based on what one wants to view

    val bookDetailsUiState: StateFlow<BookDetailsUiState> =
        bookRepository.getAllBooks().map{
            BookDetailsUiState(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = BookDetailsUiState()
        )

       companion object {
        private const val TIMEOUT_MILLIS = 5_000L //What is this?
    }

    // ChatGPT, but a bit unsure if it works correctly

     fun deleteBook(book: Book){
        viewModelScope.launch (Dispatchers.IO){
            bookRepository.deleteBook(book)

        }

    }




}

data class BookDetailsUiState(val bookList: List<Book>  = listOf())