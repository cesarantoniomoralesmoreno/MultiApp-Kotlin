package com.example.androidmaster.superheroapp

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmaster.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso


class SuperheroViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemSuperheroBinding.bind(view)

    fun bind(superheroItemResponse: SuperheroItemResponse, onItemSelected: (String) -> Unit) {
        binding.tvSuperHeroName.text = superheroItemResponse.name
        Picasso.get().load(superheroItemResponse.superheroImage.url).into(binding.ivSuperhero)

        binding.root.setOnClickListener {
            val context = it.context
            val intent = Intent(context, DetailSuperheroActivity::class.java).apply {
                putExtra(DetailSuperheroActivity.EXTRA_ID, superheroItemResponse.superheroId)
            }
            context.startActivity(intent)
        }
    }
}
