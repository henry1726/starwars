package com.example.pruebags.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pruebags.data.models.Character
import com.example.pruebags.databinding.ItemCharacterBinding

class ItemAdapter(private val context : Context,
                  private val onClickButtons: OnClickButtons) : RecyclerView.Adapter<ItemAdapter.ViewHolder> (){

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

    inner class ViewHolder(var view : ItemCharacterBinding) : RecyclerView.ViewHolder(view.root){

    }
}