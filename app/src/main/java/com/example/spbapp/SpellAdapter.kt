package com.example.spbapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_spell.view.*

class SpellAdapter(private var spells: List<Spell>) : RecyclerView.Adapter<SpellAdapter.SpellViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_spell, parent, false)
        return SpellViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpellViewHolder, position: Int) {
        val spell = spells[position]
        holder.itemView.spell_name.text = spell.name
        holder.itemView.spell_description.text = spell.description
    }

    override fun getItemCount(): Int = spells.size

    fun updateData(newSpells: List<Spell>) {
        spells = newSpells
        notifyDataSetChanged()
    }

    class SpellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}