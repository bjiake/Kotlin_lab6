package com.example.gson

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.net.URL
//1
class ImageAdapter(private val photos: List<Photo>, private val onImageClick: (String) -> Unit) : RecyclerView.Adapter<ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)

        return ImageViewHolder(view, onImageClick)
    }
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(photos[position])
    }
    override fun getItemCount(): Int = photos.size

}
class ImageViewHolder(private val itemView: View, private val onImageClick: (String) -> Unit): RecyclerView.ViewHolder(itemView){
    fun bind(photo: Photo) {
        val url:String = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_z.jpg"
        val imageView = itemView.findViewById<ImageView>(R.id.image)

        imageView.setOnClickListener {
            onImageClick(url)
        }


        Glide.with(itemView.context).load(url).into(imageView)
    }

}