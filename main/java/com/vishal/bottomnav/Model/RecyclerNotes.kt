package com.vishal.bottomnav.Model


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vishal.bottomnav.R

class RecyclerNotes(
    var context: Context,
    var notesArray: ArrayList<NotesEntity>,
    var itemClickNotes: ItemClickNotes
) : RecyclerView.Adapter<RecyclerNotes.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        var tvPersonalAssistant = view.findViewById<TextView>(R.id.tvPersonalAssistant)
        var tvPersonalNotes = view.findViewById<TextView>(R.id.tvPersonalNotes)
        var tvDateTime = view.findViewById<TextView>(R.id.tvDateTime)
        var tvPriorirty = view.findViewById<View>(R.id.viewPriority)
        var btnDelete = view.findViewById<View>(R.id.btnDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_notes, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notesArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvPersonalAssistant.setText(notesArray[position].title)
        holder.tvPersonalNotes.setText(notesArray[position].notes)
        holder.tvDateTime.setText(notesArray[position].dateTime)
        when (notesArray[position].priority) {
            1 -> {
                holder.tvPriorirty.setBackgroundResource(R.drawable.green_dot)
            }

            2 -> {
                holder.tvPriorirty.setBackgroundResource(R.drawable.yellow_dot)
            }

            3 -> {
                holder.tvPriorirty.setBackgroundResource(R.drawable.red_dot)
            }
        }
        holder.itemView.setOnClickListener {
            itemClickNotes.update(notesArray[position])
        }

        holder.btnDelete.setOnClickListener {
            itemClickNotes.delete(position)
        }
    }


}

