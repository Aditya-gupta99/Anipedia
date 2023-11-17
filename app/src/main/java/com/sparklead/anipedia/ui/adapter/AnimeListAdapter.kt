package com.sparklead.anipedia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sparklead.anipedia.databinding.ItemAnimeBinding
import com.sparklead.anipedia.model.all_anime.AnimeResponse
import com.sparklead.anipedia.utils.GlideLoader

class AnimeListAdapter(private val list: List<AnimeResponse>) :
    RecyclerView.Adapter<AnimeListAdapter.AnimeListViewHolder>() {

    var onItemClick: ((AnimeResponse) -> Unit)? = null

    inner class AnimeListViewHolder(val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListViewHolder {
        return AnimeListViewHolder(
            ItemAnimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AnimeListViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.tvTitle.text = this.title
                binding.tvRating.text = this.score.toString().substring(0, score.toString().length - 1)
                this.images?.jpg?.large_image_url?.let {
                    GlideLoader(holder.itemView.context).loadAnimePicture(
                        it,binding.ivAnime
                    )
                }
                binding.cvAnime.setOnClickListener {
                    onItemClick?.invoke(this)
                }
            }
        }
    }

}