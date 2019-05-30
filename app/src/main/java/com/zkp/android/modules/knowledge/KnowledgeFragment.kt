package com.zkp.android.modules.knowledge

import android.content.Intent
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import com.coder.zzq.smartshow.toast.SmartToast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zkp.android.R
import com.zkp.android.base.fragment.BaseFragment
import com.zkp.android.bean.KnowledgeTreeBody
import com.zkp.android.modules.knowledge.adapter.KnowledgeAdapter
import com.zkp.android.modules.knowledge.detail.KnowledgeDetailActivity

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.knowledge
 * @time: 2019/5/29 13:57
 * @description:
 */
class KnowledgeFragment : BaseFragment<KnowledgeContract.View, KnowledgeContract.Presenter>(), KnowledgeContract.View {

    @BindView(R.id.refreshLayout)
    lateinit var mRefreshLayout: SmartRefreshLayout
    @BindView(R.id.recyclerView)
    lateinit var mRecyclerView: RecyclerView

    private var mAdapter: KnowledgeAdapter? = null

    override fun getKnowledgeTreeSuccess(data: MutableList<KnowledgeTreeBody>, isFresh: Boolean) {
        if (mAdapter?.data?.size!! < data.size) {
            mAdapter?.replaceData(data)
        }
    }

    override fun getKnowledgeTreeError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    fun getInstance(): KnowledgeFragment {
        return KnowledgeFragment()
    }

    override fun createPresenter(): KnowledgeContract.Presenter = KnowledgePresenter()
    override fun getLayoutId(): Int {
        return R.layout.fragment_knowledge
    }

    override fun initView() {
        //设置布局管理器
        mRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //设置默认动画
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.setHasFixedSize(true)

        val knowledgeList = mutableListOf<KnowledgeTreeBody>()
        mAdapter = KnowledgeAdapter(R.layout.item_knowledge_hierarchy, knowledgeList)
        mRecyclerView.adapter = mAdapter

        mAdapter?.setOnItemClickListener { _, _, position ->
            val intent = Intent(activity, KnowledgeDetailActivity::class.java)
            intent.putExtra("knowledge_item", mAdapter?.data?.get(position))
            startActivity(intent)
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

        mRefreshLayout.setOnLoadMoreListener { refreshLayout ->
            mPresenter?.loadMore()
            refreshLayout.finishLoadMore()
        }
    }

    fun jumpToTop() {
        mRecyclerView.smoothScrollToPosition(0)
    }
}