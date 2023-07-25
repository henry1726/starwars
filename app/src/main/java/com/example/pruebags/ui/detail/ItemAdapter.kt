package com.example.pruebags.ui.detail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pruebags.databinding.ItemCharacterBinding
import com.example.pruebags.data.models.Character
import com.example.pruebags.data.models.CharacterResponse
import com.example.pruebags.data.models.CharactersResponse
import java.util.Locale

class ItemAdapter(private val context : Context,
                  private val onClickButtons: OnClickButtons) : RecyclerView.Adapter<ItemAdapter.ViewHolder> (), Filterable{

    private val list = ArrayList<Character>()

    fun addCharacters(listC : List<Character>){
        list.clear()
        list.addAll(listC)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val itemBinding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        holder.view.itemNameCharacter.text = list[position].name
        Glide
            .with(context)
            .load(list[position].image)
            .into(holder.view.itemImg)

        holder.view.root.setOnClickListener {
            onClickButtons.onClickCharacter(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    private val customFilter = object : Filter() {
        override fun performFiltering(query: CharSequence?): FilterResults {
            Log.d("TAG", "performFiltering: " + list.count())
            val filteredList = mutableListOf<Character>()
            if (query.isNullOrBlank()) {
                list.let { filteredList.addAll(it) }
            } else {
                for (item in list) {
                    if (item.name.contains(query, true)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults) {
            addCharacters(filterResults.values as MutableList<Character>)
        }

    }

    override fun getFilter(): Filter {
        return customFilter
    }

    inner class ViewHolder(var view : ItemCharacterBinding) : RecyclerView.ViewHolder(view.root){

    }
}