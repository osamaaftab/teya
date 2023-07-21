package com.osamaaftab.teya.presentation.filtertype

import android.widget.Filter
import com.osamaaftab.teya.domain.model.EntryModel
import com.osamaaftab.teya.presentation.adapter.GenericListAdapter

 class AlbumFilter(var listAdapter: GenericListAdapter<EntryModel>, var list: List<EntryModel>) : Filter() {

    private var filteredList: ArrayList<EntryModel> = ArrayList()

    override fun performFiltering(charSequence: CharSequence): FilterResults {
        filteredList.clear()
        val results = FilterResults()

        if (charSequence.isEmpty()) {
            filteredList.addAll(list)
        } else {
            val filterPattern: String = charSequence.toString().lowercase().trim()
            for (model in list) {
                if (model.artist.label.lowercase().contains(filterPattern)) {
                    filteredList.add(model)
                }
            }
        }
        results.values = filteredList
        results.count = filteredList.size
        return results
    }

    override fun publishResults(p0: CharSequence, result: FilterResults) {
        listAdapter.submitList(result.values as ArrayList<EntryModel>)
    }
}
