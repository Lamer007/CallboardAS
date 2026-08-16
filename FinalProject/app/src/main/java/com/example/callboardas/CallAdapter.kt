package com.example.callboardas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.round

class CallAdapter(val calls: List<CallModel>, private val context: Context): RecyclerView.Adapter<CallAdapter.ViewHolderClass>() {
    private val db: DataBase by lazy { DataBase(context) }
    var onItemClick: ((CallModel) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolderClass(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = calls[position]
        val price = round(currentItem.price.toDouble() * db.getCurrencyByName(currentItem.currency)).toInt()
        holder.rvTitle.text = currentItem.name
        holder.rvAuthor.text = currentItem.author
        holder.rvPrice.text = "${price} ${currentItem.currency}"
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(currentItem)
        }
    }
    override fun getItemCount(): Int {
        return calls.size
    }
    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvTitle:TextView = itemView.findViewById(R.id.textView)
        val rvAuthor:TextView = itemView.findViewById(R.id.textView2)
        val rvPrice:TextView = itemView.findViewById(R.id.textView3)
    }

}