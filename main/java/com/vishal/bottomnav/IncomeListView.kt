package com.vishal.bottomnav

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class IncomeListView(
    var context: Context,
    val arrayIncome: ArrayList<ExpenseEntity>,
    val listener: OnItemClickListener,
    var itemClick: ThirdFragmentInterface): RecyclerView.Adapter<IncomeListView.ViewHolder>()
{
    private var onClickListener: DialogInterface.OnClickListener? = null

     inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view),View.OnClickListener
    {
        val tvamount = view.findViewById<TextView>(R.id.tvAmount)
        val tvDate = view.findViewById<TextView>(R.id.dateAndTime)
        val cardLayout = view.findViewById<MaterialCardView>(R.id.cardLayout)
        val imgBtn = view.findViewById<ImageButton>(R.id.imgBtnDelete)
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeListView.ViewHolder
    {
        val viewIncome = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_history,parent,false)
        return ViewHolder(viewIncome)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.tvamount.text = arrayIncome[position].amount.toString()
        holder.tvDate.text = arrayIncome[position].dateTime
        holder.imgBtn.setOnClickListener{
            if(arrayIncome[position].type == 1){
                itemClick.deleteItem(position)
            }
            else{
                itemClick.deleteItemExpense(position)
            }

        }
        if(arrayIncome[position].type == 1)
            holder.cardLayout.strokeColor = ContextCompat.getColor(context, R.color.green)
        else
            holder.cardLayout.strokeColor = ContextCompat.getColor(context, R.color.red)
    }

    override fun getItemCount(): Int
    {
        return arrayIncome.size
    }

    operator fun get(position: Int): ExpenseEntity {
        return arrayIncome[position]

    }


}