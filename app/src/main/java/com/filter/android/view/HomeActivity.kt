package com.filter.android.view

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.View.INVISIBLE
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.filter.R
import com.filter.android.di.component.HomeActivityComponent
import com.filter.android.di.module.HomeActivityModule
import com.filter.android.model.remote.Resource
import com.filter.android.model.remote.Resource.Status.*
import com.filter.android.model.repository.DisplayData
import com.filter.android.viewmodel.RecyListDataViewModel
import java.util.*
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    private lateinit var mHomeActivityComponent: HomeActivityComponent
    private val mItemList = ArrayList<ItemRecyclerDisplayData>()
    private lateinit var mViewModel: RecyListDataViewModel
    private lateinit var mAdapter: RecyclerAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mIndexBarView: IndexBarView
    private lateinit var mIndexBarText: TextView
    private lateinit var mLoadFailedView: TextView
    private lateinit var mLoadingGroup: Group
    private lateinit var mTitle: TitleDecoration
    private lateinit var mCtx: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        initInjector()
        initViewModel()
        initView()
    }

    @Inject
    fun setContext(activity: HomeActivity) {
        mCtx = activity//mCtx = this;
    }

    private fun initInjector() {
        mHomeActivityComponent = getApplicationComponent()
            .homeActivityComponent()
            .homeActivityModule(HomeActivityModule(this))
            .build()
        mHomeActivityComponent.inject(this)
    }

    private fun initViewModel() {
        mViewModel = ViewModelProviders.of(this@HomeActivity).get(RecyListDataViewModel::class.java)
        mViewModel.getDataByFilter().observe(this, observerFilterData)
    }

    private fun initView() {
        buildToolBarView()
        buildRecyclerView()
        buildIndexBarView()
        buildLoadingView()
    }

    private fun buildToolBarView() {

        val toolbar = findViewById<Toolbar>(R.id.tool_bar)
        setSupportActionBar(toolbar)
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(mCtx)

        mAdapter = RecyclerAdapter(mCtx)
        mAdapter.setItemClickListener(itemListener)
        mRecyclerView.adapter = mAdapter

        mTitle = TitleDecoration(mCtx)

        mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        mRecyclerView.addItemDecoration(mTitle)
    }

    private fun buildIndexBarView() {
        mIndexBarView = findViewById(R.id.index_bar)
        mIndexBarText = findViewById(R.id.index_bar_text)
        mIndexBarText.visibility = INVISIBLE//Default

        mIndexBarView.setHintText(mIndexBarText)
        mIndexBarView.setOnTouchEventListener(indexListener)
    }

    private fun buildLoadingView() {
        mLoadFailedView = findViewById(R.id.load_failInfo)
        mLoadingGroup = findViewById(R.id.group_loading)

        mLoadFailedView.visibility = INVISIBLE//Default
    }

    private val indexListener = object : IndexBarView.OnTouchEventListener {
        override fun onTouchListener(it: String) {
            val position = mAdapter.getPositionByIndex(it)
            if (position != RecyclerView.NO_POSITION) {
                //mRecyclerView.scrollToPosition(position);
                (mRecyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, 0)
            }
        }
    }

    private val itemListener = object : RecyclerAdapter.OnItemClickListener {
        override fun onItemClick(position: Int) {
            if (position != RecyclerView.NO_POSITION) {
                //To Be extension
            }
        }
    }

    private val observerFilterData =
        Observer<Resource<DisplayData>> { it ->
            it?.let {
                updateViewStatus(it.mStatus)
                if (it.mStatus == SUCCESS) {
                    updateViewData(it.mData);
                }
            }
        }

    private val searchListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(s: String): Boolean {
            return false
        }

        override fun onQueryTextChange(s: String): Boolean {
            //Toast.makeText(mCtx, "Start to Search " + s, Toast.LENGTH_SHORT).show();
            mViewModel.setFilter(s)
            return false
        }
    }

    private fun updateViewStatus(status: Resource.Status) {
        mLoadingGroup.visibility = if (status == LOADING) View.VISIBLE else INVISIBLE
        mLoadFailedView.visibility = if (status == ERROR) View.VISIBLE else INVISIBLE
        mRecyclerView.visibility = if (status == SUCCESS) View.VISIBLE else INVISIBLE
        //mIndexBarText is decided by ACTION UP DOWN or MOVE
        mIndexBarView.visibility = if (status == SUCCESS) View.VISIBLE else INVISIBLE
    }

    private fun updateViewData(listData: DisplayData?) {
        listData?.let {
            prepareItemListData(listData)
            mAdapter.setData(mItemList)
            mTitle.setData(mItemList)
        }
    }

    private fun prepareItemListData(data: DisplayData) {
        mItemList.clear()
        data.item?.let {
            for (i in it) {
                mItemList.add(ItemRecyclerDisplayData(i.name, i.imagesUrl))
            }
        }
        mItemList.sortBy({ it.name })//ascending order
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchItem.actionView as SearchView

        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(searchListener)
        //searchView.setQueryHint(DEFAULT_SEARCH_VIEW_HINT);
        //searchView.setIconifiedByDefault(false);//Icon always display
        return true
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}