package com.vishal.bottomnav

import android.widget.ListAdapter
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseHistoryDao
{
    @Insert
    fun insertExpenseData(expenseEntity: ExpenseEntity): Long
    @Query("Select * From expenseentity where type = :type")
    fun getExpenseData(type: Int): List<ExpenseEntity>

    @Query("Select SUM(amount) From expenseentity where type = :type")
    fun getExpenseTotal(type: Int): Long
    @Delete
    fun delete(expenseEntity: ExpenseEntity)
}