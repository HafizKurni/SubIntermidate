package com.dicoding.subintermidate.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.subintermidate.data.local.story.Story

import com.dicoding.subintermidate.databinding.StoryShowBinding

class ShowAdapter: RecyclerView.Adapter<ShowAdapter.ShowViewHolder>() {

    private val list = ArrayList<Story>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(story: List<Story>){
        list.addAll(story)
        notifyDataSetChanged()
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    inner class ShowViewHolder(private val binding: StoryShowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(story: Story) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(story)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(story.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivStory)
                tvNama.text = story.name
                tvDesc.text = story.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val view = StoryShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        holder.bind(list[position])
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: Story)
    }
}

