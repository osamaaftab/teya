package com.osamaaftab.teya.presentation.viewholder

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.osamaaftab.teya.presentation.adapter.GenericListAdapter
import com.osamaaftab.teya.BR
import com.osamaaftab.teya.domain.model.EntryModel
import com.osamaaftab.teya.presentation.adapter.GenericPagerAdapter
import com.osamaaftab.teya.presentation.ui.fragment.ImageFragment
import com.osamaaftab.teya.presentation.common.PagerItem


class AlbumViewHolder<T>(
    private val viewDataBinding: ViewDataBinding,
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle
) : RecyclerView.ViewHolder(viewDataBinding.root),
    GenericListAdapter.Binder<T> {

    override fun bind(data: T) {
        viewDataBinding.setVariable(BR.album, data)
        viewDataBinding.setVariable(BR.galleryAdpater,initGalleryAdapter(data as EntryModel))
    }

    private fun initGalleryAdapter(data: EntryModel): GenericPagerAdapter  {
        val list: ArrayList<PagerItem> = ArrayList()
        for (image in data.imageList) {
            list.add(PagerItem(ImageFragment.newInstance(image.label)))
        }
        return object : GenericPagerAdapter(fragmentManager, lifecycle) {
            override fun getViewsCount(): Int {
                return list.size
            }

            override fun getPagerItem(): ArrayList<PagerItem> {
                return list
            }
        }
    }
}