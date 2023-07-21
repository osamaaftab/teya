package com.osamaaftab.teya.presentation.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Filter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.osamaaftab.teya.R
import com.osamaaftab.teya.databinding.ActivityAlbumListBinding
import com.osamaaftab.teya.domain.model.EntryModel
import com.osamaaftab.teya.presentation.adapter.GenericListAdapter
import com.osamaaftab.teya.presentation.diffutil.AlbumDiffUtil
import com.osamaaftab.teya.presentation.filtertype.AlbumFilter
import com.osamaaftab.teya.presentation.viewholder.AlbumViewHolder
import com.osamaaftab.teya.presentation.viewmodel.AlbumViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AlbumListActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val albumViewModel: AlbumViewModel by viewModel()
    private var albumListAdapter: GenericListAdapter<EntryModel>? = null
    private lateinit var activityAlbumListBinding: ActivityAlbumListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAlbumListBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_album_list)
        initListener()
        initAdapter()
        initObserver()
        albumViewModel.loadAlbumList()
    }

    private fun initAdapter() {
       val  albumListAdapter = object : GenericListAdapter<EntryModel>(AlbumDiffUtil()) {

            override fun getViewHolder(viewDataBinding: ViewDataBinding): RecyclerView.ViewHolder {
                return AlbumViewHolder<EntryModel>(
                    viewDataBinding,
                    supportFragmentManager,
                    lifecycle
                )
            }

            override fun getLayoutId(): Int {
                return R.layout.item_album
            }

            override fun getFilterType(): Filter {
                return AlbumFilter(this, this.currentList)
            }
        }
        activityAlbumListBinding.albumRecyclerView.adapter = albumListAdapter
    }

    private fun initObserver() {
        observeAlbumListLiveData()
        observerProgressLiveData()
        observeShowErrorLiveData()
    }

    private fun observeShowErrorLiveData() {
        albumViewModel.getShowErrorLiveData().observe(this) {
            activityAlbumListBinding.refreshLayout.isRefreshing = false
            if (it == true) {
                activityAlbumListBinding.statusLbl.visibility = View.VISIBLE
            } else activityAlbumListBinding.statusLbl.visibility = View.GONE
        }
    }

    private fun observerProgressLiveData() {
        albumViewModel.getShowProgressLiveData().observe(this) {
            if (it == true) {
                activityAlbumListBinding.indeterminateBar.visibility = View.VISIBLE
            } else activityAlbumListBinding.indeterminateBar.visibility = View.GONE
        }
    }

    private fun observeAlbumListLiveData() {
        albumViewModel.getAlbumListLiveData().observe(this) {
            albumListAdapter?.submitList(it) {
                activityAlbumListBinding.albumRecyclerView.scrollToPosition(0)
            }
            activityAlbumListBinding.albumRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun initListener() {
        activityAlbumListBinding.refreshLayout.setOnRefreshListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options menu from XML
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconified = true
        }
        return true
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            albumListAdapter?.filter?.filter(query)
        }
    }

    override fun onRefresh() {
        albumViewModel.refreshAlbumList()
    }
}