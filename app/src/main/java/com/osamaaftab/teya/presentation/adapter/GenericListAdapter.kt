package com.osamaaftab.teya.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


abstract class GenericListAdapter<T>(diffUtil: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, RecyclerView.ViewHolder>(diffUtil), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holderDataBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), getLayoutId(), parent, false
        )
        return getViewHolder(holderDataBinding)
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun getFilterType(): Filter

    abstract fun getViewHolder(viewDataBinding: ViewDataBinding): RecyclerView.ViewHolder

    override fun getFilter(): Filter = getFilterType()

    internal interface Binder<in U> {
        fun bind(data: U)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Binder<T>).bind(getItem(position))
    }
}