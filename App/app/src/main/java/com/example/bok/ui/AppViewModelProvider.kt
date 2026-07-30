package com.example.bok.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bok.BookApplication
import com.example.bok.ui.book.BookDetailsViewModel
import com.example.bok.ui.book.BookEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            BookEntryViewModel(bookApplication().container.bookRepository)
        }

        initializer {
            BookDetailsViewModel(bookApplication().container.bookRepository)
        }

        // Initialize other viewModels if you have them
    }
}


// I do not know what this does
fun CreationExtras.bookApplication(): BookApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as BookApplication)