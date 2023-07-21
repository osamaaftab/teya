package com.osamaaftab.teya.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.osamaaftab.teya.domain.model.EntryModel

class AlbumDiffUtil : DiffUtil.ItemCallback<EntryModel>() {
    override fun areItemsTheSame(oldItem: EntryModel, newItem: EntryModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: EntryModel, newItem: EntryModel): Boolean {
        return oldItem.id == newItem.id
    }
}