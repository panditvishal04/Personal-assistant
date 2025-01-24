package com.vishal.bottomnav.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Calendar

@Entity
data class NotesEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,

    @ColumnInfo()
    var title : String?= null,

    @ColumnInfo()
    var subtitle : String?= null,

    @ColumnInfo()
    var notes : String?= null,

    @ColumnInfo()
    var priority : Int? = null,

    @ColumnInfo()
    var dateTime : String?= SimpleDateFormat("dd/MM/yyyy hh:mm aa").format(Calendar.getInstance().time)
)




