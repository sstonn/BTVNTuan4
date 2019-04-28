package com.hnam.recyclerview.adapter

import android.content.Context
import com.hnam.recyclerview.model.Movie
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.hnam.recyclerview.MovieItemClickListener
import com.hnam.recyclerview.R
import com.hnam.recyclerview.drawableByName
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(var items:ArrayList<Movie>, val context: Context): RecyclerView.Adapter<MovieViewHolder>(){
    lateinit var mListener: MovieItemClickListener
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie,p0,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: MovieViewHolder, p1: Int) {
        p0.title.text="#$p1 ${items[p1].title}"
        p0.description.text=items[p1].overview
        Glide.with(context)
            .load( context.drawableByName( items[p1].poster_path))
            .centerCrop()
            .placeholder(R.drawable.congphuong)
            .into(p0.item_image)
        p0.itemView.setOnClickListener {
            mListener.onItemCLicked(p1)
        }
    }
    fun setListener(listener: MovieItemClickListener) {
        this.mListener = listener
    }

    fun setData(items: ArrayList<Movie>){
        this.items = items
        notifyDataSetChanged()
    }
}
class MovieViewHolder(view: View):RecyclerView.ViewHolder(view){
    var title=view.title
    var description=view.description
    var item_image=view.item_image
}