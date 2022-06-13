package com.dicoding.dikasirind.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.dikasirind.R
import com.dicoding.dikasirind.model.UserData

class PostAdapter(private var list: ArrayList<UserData>): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var nam: TextView
        var stoc: TextView
        init {
            stoc = itemView.findViewById(R.id.jumlahBrg)
            nam = itemView.findViewById(R.id.namaBrg)
        }
        fun bind(userData: UserData){
            with(itemView){
                val nama  = "${userData.nama}"
                val stock = "${userData.stock}"

                nam.text = nama
                stoc.text = stock
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val froms = R.layout.list
        val view = LayoutInflater.from(parent.context).inflate(froms, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int=list.size
}