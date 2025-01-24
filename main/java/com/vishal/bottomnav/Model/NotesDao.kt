package com.vishal.bottomnav.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.text.ParsePosition


@Dao
interface NotesDao {

    @Insert
    fun insertNotes(notes : NotesEntity)

    @Query("SELECT * FROM NotesEntity")
    fun getNotes() : List<NotesEntity>

    @Update
    fun updateNotes(notes : NotesEntity)

    @Delete
    fun deleteNotes(notes: NotesEntity)

    @Query("SELECT * FROM NotesEntity WHERE id = :id")
    fun getNotesEntityId(id : Int): NotesEntity
}