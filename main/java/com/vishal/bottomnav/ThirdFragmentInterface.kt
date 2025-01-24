package com.vishal.bottomnav

import androidx.room.Delete
import java.text.FieldPosition

interface ThirdFragmentInterface
{
  @Delete
   fun deleteItem(position: Int)

    @Delete
    fun deleteItemExpense(position: Int)
}