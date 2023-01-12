package com.phuayanhan.wordpad.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.phuayanhan.wordpad.data.Model.Word

@Dao
interface WordDao {

    @Query("SELECT * FROM word")
    suspend fun getWords():List<Word>

    @Query("SELECT * FROM word where id=:id")
    suspend fun getWordById(id:Int):Word

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word WHERE id=:id")
    suspend fun delete(id: Int)

    @Query("UPDATE word SET completed = :status WHERE id = :id")
    suspend fun updateStatusById(id: Long, status: Boolean)

    @Query("SELECT * FROM word WHERE title LIKE '%'|| :title ||'%'")
    suspend fun getWordsBySearch(title: String): List<Word>
}