package com.vishal.bottomnav.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vishal.bottomnav.ExpenseEntity
import com.vishal.bottomnav.ExpenseHistoryDao
import com.vishal.bottomnav.R


@Database(version = 1 , entities = [NotesEntity::class,ExpenseEntity::class])
abstract class NotesDataBase : RoomDatabase() {

    abstract fun NotesDao() : NotesDao
    abstract fun ExpenseHistoryDao(): ExpenseHistoryDao

    companion object{
        private var roomDbNotes : NotesDataBase  ?= null

        fun createDatabase(context: Context) : NotesDataBase{
            if(roomDbNotes == null){
                roomDbNotes = Room.databaseBuilder(context,
                    NotesDataBase::class.java,
                    context.resources.getString(R.string.app_name)).build()
            }
            return roomDbNotes!!
        }
    }
}