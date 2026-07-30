package com.example.bok.data

import android.content.Context
import com.example.bok.data.database.BookDatabase
import com.example.bok.data.repository.BookRepository
import com.example.bok.data.repository.OfflineBookRepository


// https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#7
interface AppContainer{
    val bookRepository: BookRepository
}


class AppDataContainer(private val context: Context) : AppContainer {
    override val bookRepository: BookRepository by lazy {
        OfflineBookRepository(BookDatabase.getDatabase(context).bookDao())
    }
}