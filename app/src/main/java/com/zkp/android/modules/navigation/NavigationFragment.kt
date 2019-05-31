package com.zkp.android.modules.navigation

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import com.coder.zzq.smartshow.toast.SmartToast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zkp.android.R
import com.zkp.android.base.fragment.BaseFragment
import com.zkp.android.bean.Navigation
import com.zkp.android.modules.navigation.adapter.NavigationAdapter
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.navigation
 * @time: 2019/5/30 11:37
 * @description:
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
        mLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.setHasFixedSize(true)

        mNavigationList = mutableListOf()
        mAdapter = NavigationAdapter(R.layout.item_navigation, mNavigationList)
        mRecyclerView.adapter = mAdapter

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