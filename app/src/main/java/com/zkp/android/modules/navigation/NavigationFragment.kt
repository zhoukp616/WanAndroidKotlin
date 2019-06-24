package com.zkp.android.modules.navigation

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import com.coder.zzq.smartshow.toast.SmartToast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zkp.android.R
import com.zkp.android.app.App
import com.zkp.android.base.fragment.BaseFragment
import com.zkp.android.bean.Navigation
import com.zkp.android.modules.navigation.adapter.NavigationAdapter
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView
import q.rorbin.verticaltablayout.widget.TabView

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.navigation
 * @time: 2019/5/30 11:37
 * @description: 导航页面
 */
class NavigationFragment : BaseFragment<NavigationContract.View, NavigationContract.Presenter>(),
    NavigationContract.View {

    @BindView(R.id.refreshLayout)
    lateinit var mRefreshLayout: SmartRefreshLayout

    @BindView(R.id.tabLayout)
    lateinit var mTabLayout: VerticalTabLayout

    @BindView(R.id.recyclerView)
    lateinit var mRecyclerView: RecyclerView

    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: NavigationAdapter
    private lateinit var mNavigationList: MutableList<Navigation>

    private var needScroll: Boolean = false
    private var index: Int = 0
    private var isClickTab: Boolean = false

    fun getInstance(): NavigationFragment {
        return NavigationFragment()
    }

    override fun getNaviJsonSuccess(navigationList: MutableList<Navigation>, isFresh: Boolean) {
        this.mNavigationList = navigationList

        if (isFresh) {
            mAdapter.replaceData(mNavigationList)

            mTabLayout.setTabAdapter(object : TabAdapter {
                override fun getCount(): Int {
                    return mNavigationList.size
                }

                override fun getBadge(i: Int): ITabView.TabBadge? {
                    return null
                }

                override fun getIcon(i: Int): ITabView.TabIcon? {
                    return null
                }

                override fun getTitle(i: Int): ITabView.TabTitle {
                    return ITabView.TabTitle.Builder()
                        .setContent(mNavigationList[i].name)
                        .setTextColor(
                            ContextCompat.getColor(context!!, R.color.colorWhite),
                            ContextCompat.getColor(context!!, R.color.Grey500)
                        )
                        .build()
                }

                override fun getBackground(i: Int): Int {
                    return 0
                }
            })

        } else {
            mAdapter.addData(mNavigationList)
        }
        mTabLayout.setTabSelected(0)
    }

    override fun getNaviJsonError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun createPresenter(): NavigationContract.Presenter = NavigationPresenter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_navigation
    }

    override fun initView() {

        App().addFragment(this)

        mLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.setHasFixedSize(true)

        mNavigationList = mutableListOf()
        mAdapter = NavigationAdapter(R.layout.item_navigation, mNavigationList)
        mRecyclerView.adapter = mAdapter

        //联动控件 上下同步
        combine()
    }

    /**
     * 组合VerticalTabLayout和RecyclerView,使它们在垂直方向上滑动同步
     */
    private fun combine() {
        //1.RecyclerView滚动时控制VerticalTabLayout滚动
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (needScroll && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    scrollRecyclerView()
                }

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (isClickTab) {
                        isClickTab = false
                        return
                    }
                    val firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition()
                    if (index != firstPosition) {
                        index = firstPosition
                        setChecked(index)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (needScroll) {
                    scrollRecyclerView()
                }
            }
        })

        //1.VerticalTabLayout滚动时控制RecyclerView滚动
        mTabLayout.addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tabView: TabView, i: Int) {
                isClickTab = true
                index = i
                mRecyclerView.stopScroll()
                smoothScrollToPosition(i)
            }

            override fun onTabReselected(tabView: TabView, i: Int) {}
        })
    }

    private fun scrollRecyclerView() {
        needScroll = false
        val indexDistance = index - mLinearLayoutManager.findFirstVisibleItemPosition()
        if (indexDistance >= 0 && indexDistance < mRecyclerView.childCount) {
            val top = mRecyclerView.getChildAt(indexDistance).top
            mRecyclerView.smoothScrollBy(0, top)
        }
    }

    private fun setChecked(position: Int) {
        if (isClickTab) {
            isClickTab = false
        } else {
            mTabLayout.setTabSelected(index)
        }
        index = position
    }

    private fun smoothScrollToPosition(currentPosition: Int) {
        val firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition()
        val lastPosition = mLinearLayoutManager.findLastVisibleItemPosition()
        if (currentPosition <= firstPosition) {
            mRecyclerView.smoothScrollToPosition(currentPosition)
        } else if (currentPosition <= lastPosition) {
            val top = mRecyclerView.getChildAt(currentPosition - firstPosition).top
            mRecyclerView.smoothScrollBy(0, top)
        } else {
            mRecyclerView.smoothScrollToPosition(currentPosition)
            needScroll = true
        }
    }

    override fun initEventAndData() {

        initRefreshLayout()

        mPresenter?.refresh()
    }

    private fun initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener { refreshLayout ->
            mPresenter?.refresh()
            refreshLayout.finishRefresh()
        }
    }

    fun jumpToTop() {
        mRecyclerView.smoothScrollToPosition(0)
    }
}