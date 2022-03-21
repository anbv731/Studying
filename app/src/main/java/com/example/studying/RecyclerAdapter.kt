package com.example.studying

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val list: List<Something>) :
    RecyclerView.Adapter<RecyclerAdapter.SomethingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SomethingViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return SomethingViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: SomethingViewHolder, position: Int) {
        holder.textViewId.text = (list.get(position).id.toString())
        holder.textViewTitle.text = (list.get(position).title)
        holder.textViewBody.text = (list.get(position).body)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class SomethingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewId: TextView = itemView.findViewById(R.id.textId)
        val textViewTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textViewBody: TextView = itemView.findViewById(R.id.textBody)
    }
}