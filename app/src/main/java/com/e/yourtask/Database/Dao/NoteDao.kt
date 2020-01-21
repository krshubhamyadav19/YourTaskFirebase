package com.e.yourtask.Database.Dao

import androidx.room.*
import com.e.yourtask.Database.Entity.Note


@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table")
    fun getAll(): List<Note>

    @Query("SELECT * FROM note_table WHERE title LIKE :title")
    fun findByTitle(title: String): Note

    @Insert
    fun insertAll( note: Note)

    @Delete
    fun remove(note: Note)

    @Update
    fun updateNote( note: Note)
}