package com.example.catbreeds.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.catbreeds.model.Cat

@Dao
interface CatDAO {

    @Insert
    suspend fun insertFavoriteCatBreed(vararg cat: Cat): List<Long>

    @Query("SELECT * FROM cat")
    suspend fun getAllFavoriteCatBreeds(): List<Cat>

    @Query("SELECT * FROM cat WHERE id = :apiId")
    suspend fun getCatBreed(apiId: String): Cat

    @Query("DELETE FROM cat WHERE id = :apiId")
    suspend fun deleteFavoriteCatBreed(apiId: String)
}