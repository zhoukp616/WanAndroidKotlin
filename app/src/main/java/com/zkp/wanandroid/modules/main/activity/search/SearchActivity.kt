package com.zkp.wanandroid.modules.main.activity.search

import android.content.Intent
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.coder.zzq.smartshow.toast.SmartToast
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import com.zkp.wanandroid.R
import com.zkp.wanandroid.base.activity.BaseActivity
import com.zkp.wanandroid.bean.Hotkey
import com.zkp.wanandroid.db.entity.SearchHistory
import com.zkp.wanandroid.http.AppConfig
import com.zkp.wanandroid.modules.main.activity.adapter.SearchHistoryAdapter
import com.zkp.wanandroid.modules.main.activity.component.ComponentActivity
import java.lang.reflect.Field

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.search
 * @time: 2019/6/4 14:40
 * @description:
 */
class SearchActivity : BaseActivity<SearchContract.View, SearchContract.Presenter>(), SearchContract.View {

    @BindView(R.id.toolbar)
    lateinit var mToolbar: Toolbar

    @BindView(R.id.tagFlowLayout)
    lateinit var mTagFlowLayout: TagFlowLayout

    @BindView(R.id.recyclerView)
    lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapter: SearchHistoryAdapter
    private lateinit var mSearchHistoryList: List<SearchHistory>

    override fun createPresenter(): SearchContract.Presenter = SearchPresenter()

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mToolbar.setNavigationOnClickListener { onBackPressedSupport() }
    }

    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.setHasFixedSize(true)

        mSearchHistoryList = mutableListOf()
        mAdapter = SearchHistoryAdapter(R.layout.item_search_list, mSearchHistoryList)

        mRecyclerView.adapter = mAdapter
        mAdapter.bindToRecyclerView(mRecyclerView)
        mAdapter.setEmptyView(R.layout.search_empty_view)

    }

    override fun initEventAndData() {

        mPresenter?.getHotKeys()

        mAdapter.setOnItemChildClickListener { _, _, position ->
            if (mAdapter.data.size <= 0 || mAdapter.data.size < position) {
                return@setOnItemChildClickListener
            }
            mPresenter?.deleteSearchHistoryById(mAdapter.data[position].id)
            mAdapter.remove(position)
        }

        mAdapter.setOnItemClickListener { _, _, position ->
            if (mAdapter.data.size <= 0 || mAdapter.data.size < position) {
                return@setOnItemClickListener
            }
            goToSearchResult(mAdapter.data[position].data)
        }

    }

    override fun getHotKeysSuccess(hotkeys: MutableList<Hotkey>) {

        mTagFlowLayout.adapter = object : TagAdapter<Hotkey>(hotkeys) {
            override fun getView(parent: FlowLayout, position: Int, dataBean: Hotkey?): View {
                val textView = LayoutInflater.from(this@SearchActivity)
                    .inflate(R.layout.item_flow_layout, parent, false) as TextView
                if (dataBean != null) {
                    textView.text = dataBean.name
                }
                return textView
            }
        }

        mTagFlowLayout.setOnTagClickListener { _, position, _ ->
            goToSearchResult(hotkeys[position].name)
            true
        }

    }

    override fun onResume() {
        super.onResume()
        mPresenter?.loadAllSearchHistory()
    }

    @OnClick(R.id.tvClearAll)
    internal fun onClick(view: View) {
        when (view.id) {
            R.id.tvClearAll -> {
                mPresenter?.clearAllSearchHistory()
                mPresenter?.loadAllSearchHistory()
            }
        }
    }

    private fun goToSearchResult(keyWord: String) {
        mPresenter?.addSearchHistory(keyWord)
        val intent = Intent(this@SearchActivity, ComponentActivity::class.java)
        intent.putExtra("type_fragment", AppConfig().TYPE_SEARCH_RESULT)
        intent.putExtra("search_key", keyWord)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.search_button)
        val mSearchView = MenuItemCompat.getActionView(searchItem) as SearchView
        mSearchView.maxWidth = Integer.MAX_VALUE
        mSearchView.onActionViewExpanded()
        mSearchView.queryHint = getString(R.string.search_tint)

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                goToSearchResult(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        mSearchView.isSubmitButtonEnabled = true

        val field: Field
        try {
            field = mSearchView.javaClass.getDeclaredField("mGoButton")
            field.isAccessible = true
            val mGoButton = field.get(mSearchView) as ImageView
            mGoButton.setImageResource(R.drawable.ic_search)
        } catch (e: java.lang.NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun getHotKeysError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun showSearchHistory(historyDataList: List<SearchHistory>) {
        mSearchHistoryList = historyDataList
        mAdapter.replaceData(historyDataList)
    }

}