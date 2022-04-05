package com.example.studying

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapterStaff(private val list: List<StaffEntity>) :
    RecyclerView.Adapter<RecyclerAdapterStaff.ContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ContactViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.textViewTitle.text = (list.get(position).name)
        holder.textAge.text = ("Age "+list.get(position).age)
        holder.textExp.text = ("Experience "+list.get(position).experience)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textName)
        val textAge:TextView=itemView.findViewById(R.id.textAge)
        val textExp:TextView=itemView.findViewById(R.id.textExp)
    }
}