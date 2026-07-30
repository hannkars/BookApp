package com.example.bok.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Book::class], version = 1, exportSchema = false) //The version might have to be changed if changes happen?
@TypeConverters(Converters::class)
abstract class BookDatabase: RoomDatabase(){
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var Instance: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase{
            return Instance?: synchronized(this){
                Room.databaseBuilder(context, BookDatabase::class.java, "book_database").build().also { Instance = it } // or should name be "item_database"
            }
        }
    }
}