package com.example.bok.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bok.data.enum_classes.Genre
import com.example.bok.data.enum_classes.Language
import com.example.bok.data.enum_classes.ReadingStatus

@Entity
data class Book(
    @PrimaryKey(autoGenerate = true)
    val bid: Int = 0,

    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name= "authors") val authors: String,
    @ColumnInfo(name= "number_of_pages") val numberOfPages: Int,

    @ColumnInfo(name= "language") val language: Language,
    @ColumnInfo(name = "isbn") val isbn: String, // A bit unsure about this one

    @ColumnInfo(name = "genre") val genre: Genre,
    @ColumnInfo(name = "pub_year") val pubYear: Int, //Maybe Date or String

    @ColumnInfo(name = "reading_status") val readingStatus: ReadingStatus,
    @ColumnInfo(name = "possess") val doPossess: Boolean,
)
