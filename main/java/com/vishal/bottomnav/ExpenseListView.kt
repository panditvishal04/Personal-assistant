package com.vishal.bottomnav

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseListView(var arrayExpense: ArrayList<ExpenseEntity>,var itemClick: ThirdFragmentInterface): RecyclerView.Adapter<ExpenseListView.ViewHolder>()
{
    class ViewHolder(private val view: View): RecyclerView.ViewHolder(view)
    {
        val tvamount = view.findViewById<TextView>(R.id.tvAmount)!!
        val tvcategory = view.findViewById<TextView>(R.id.tvCategory)!!
        val tvDate = view.findViewById<TextView>(R.id.dateAndTime)
        val ImgBTN = view.findViewById<ImageButton>(R.id.imgBtnDelete)
    }
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ExpenseListView.ViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_history,parent,false)
        return ViewHolder(view)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ExpenseListView.ViewHolder, position: Int)
    {
        holder.tvamount.text = arrayExpense[position].amount.toString()
        holder.tvcategory.text = arrayExpense[position].category
        holder.tvDate.text = arrayExpense[position].dateTime

    }

    override fun getItemCount(): Int
    {
        return arrayExpense.size
    }
}
