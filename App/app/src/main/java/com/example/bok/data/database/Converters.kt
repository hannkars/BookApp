package com.example.bok.data.database

import androidx.room.TypeConverter
import com.example.bok.data.enum_classes.Genre
import com.example.bok.data.enum_classes.Language
import com.example.bok.data.enum_classes.ReadingStatus

class Converters {

    @TypeConverter
    fun fromReadingStatus(readingStatus: ReadingStatus): String {
        return readingStatus.name
    }

    @TypeConverter
    fun toReadingStatus(value: String): ReadingStatus {
        return ReadingStatus.valueOf(value)
    }


    @TypeConverter
    fun fromLanguage(language: Language): String {
        return language.name
    }

    @TypeConverter
    fun toLanguage(value: String): Language {
        return Language.valueOf(value)
    }

    @TypeConverter
    fun fromGenre(genre: Genre): String {
        return genre.name
    }

    @TypeConverter
    fun toGenre(value: String): Genre {
        return Genre.valueOf(value)
    }


}