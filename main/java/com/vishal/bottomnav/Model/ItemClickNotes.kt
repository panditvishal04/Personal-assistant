package com.vishal.bottomnav.Model

import java.text.ParsePosition

interface ItemClickNotes {

    fun update(notesEntity: NotesEntity)
    fun delete(position: Int)
    fun getNotesData()
}