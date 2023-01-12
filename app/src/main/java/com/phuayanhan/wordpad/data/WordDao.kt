package com.phuayanhan.wordpad.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.phuayanhan.wordpad.data.Model.Word

@Dao
interface WordDao {

    // to get words from the local database
    @Query("SELECT * FROM word")
    suspend fun getWords():List<Word>

    // to get words by id from the local database
    @Query("SELECT * FROM word where id=:id")
    suspend fun getWordById(id:Int):Word

    // to add word to the local database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    // to delete words from the local database
    @Query("DELETE FROM word WHERE id=:id")
    suspend fun delete(id: Int)

    // to update words from the local database
    @Query("UPDATE word SET completed = :status WHERE id = :id")
    suspend fun updateStatusById(id: Long, status: Boolean)

    // to search from the local database for a specific words
    @Query("SELECT * FROM word WHERE title LIKE '%'|| :title ||'%'")
    suspend fun getWordsBySearch(title: String): List<Word>
}