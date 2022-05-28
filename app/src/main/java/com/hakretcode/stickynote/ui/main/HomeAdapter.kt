package com.hakretcode.stickynote.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hakretcode.stickynote.data.model.Note
import com.hakretcode.stickynote.databinding.MainNoteItemBinding
import java.util.function.Consumer

class MainAdapter(
    private val onClick: (Note) -> Unit
) : ListAdapter<Note, Holder>(object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem
    }) {
    private var selectModeEnabled = false
    private val holderSet: MutableSet<Holder> = mutableSetOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = MainNoteItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        binding.onItemClick = Consumer { note -> setupClick(binding, note) }
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holderSet.add(holder)
        val binding = holder.binding
        val note = getItem(position)
        binding.note = note
        if (selectModeEnabled) {
            binding.checkBox.isChecked = note.selected
        }
    }

    private fun setupClick(binding: MainNoteItemBinding, note: Note) {
        if (selectModeEnabled) {
            val checkBox = binding.checkBox
            checkBox.toggle()
            note.selected = checkBox.isChecked
        } else onClick(note)
    }

    fun toggleSelectMode(): Boolean {
        val selectModeEnabled = !selectModeEnabled
        this.selectModeEnabled = selectModeEnabled
        for (holder in holderSet) {
            val checkBox = holder.binding.checkBox
            checkBox.visibility = if (selectModeEnabled) View.VISIBLE
            else {
                checkBox.isChecked = false
                View.GONE
            }
        }
        return selectModeEnabled
    }
}

class Holder(val binding: MainNoteItemBinding) : RecyclerView.ViewHolder(binding.root)
