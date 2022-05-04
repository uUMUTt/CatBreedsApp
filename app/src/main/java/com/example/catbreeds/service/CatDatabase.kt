package com.example.catbreeds.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.catbreeds.dao.CatDAO
import com.example.catbreeds.model.Cat

@Database(entities = [Cat::class], version = 1)
abstract class CatDatabase : RoomDatabase() {

    abstract fun catDao(): CatDAO

    companion object {
        @Volatile
        private var instance: CatDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createCatDB(context).also {
                instance = it
            }
        }

        private fun createCatDB(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CatDatabase::class.java,
            "CatDatabase"
        ).build()
    }
}