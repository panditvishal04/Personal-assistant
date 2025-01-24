package com.vishal.bottomnav

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Calendar

@Entity
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    var TransactionID: Int = 0,

    @ColumnInfo
    var amount: Double,

    @ColumnInfo
    var category: String,

    @ColumnInfo
    var paidMethod: String,

    @ColumnInfo()
    var dateTime : String?= SimpleDateFormat("dd/MM/yyyy hh:mm aa").format(Calendar.getInstance().time),

    @ColumnInfo
    var note: String,

    @ColumnInfo
    var type: Int?= 0

)