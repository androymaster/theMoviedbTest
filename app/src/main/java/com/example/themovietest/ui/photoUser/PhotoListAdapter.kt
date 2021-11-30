package com.example.themovietest.ui.photoUser

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovietest.core.BaseViewHolder
import com.example.themovietest.data.model.ImageList
import com.example.themovietest.databinding.ImagesItemViewBinding

class PhotoListAdapter(private val images: List<ImageList>) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemViewBinding = ImagesItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoUserViewHolder(itemViewBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is PhotoUserViewHolder -> holder.bind(images[position])
        }
    }

    override fun getItemCount(): Int = images.size

    private inner class PhotoUserViewHolder(
        val binding: ImagesItemViewBinding,
        val context: Context
    ):BaseViewHolder<ImageList>(binding.root){
        override fun bind(item: ImageList) {
            binding.imageTimestamp.text = "Hace 2 horas"
            Glide.with(context).load(item.photo_user_image).centerCrop().into(binding.imageUser)
        }
    }
}