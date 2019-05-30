package com.zkp.android.modules.project.list

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import com.coder.zzq.smartshow.toast.SmartToast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zkp.android.R
import com.zkp.android.base.fragment.BaseFragment
import com.zkp.android.bean.Article
import com.zkp.android.modules.home.detail.ArticleDetailActivity
import com.zkp.android.modules.project.adapter.ProjectListAdapter

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.project.list
 * @time: 2019/5/30 15:22
 * @description:
 */
class ProjectListFragment : BaseFragment<ProjectListContract.View, ProjectListContract.Presenter>(),
    ProjectListContract.View {

    @BindView(R.id.refreshLayout)
    lateinit var mRefreshLayout: SmartRefreshLayout

    @BindView(R.id.recyclerView)
    lateinit var mRecyclerView: RecyclerView

    private var cid: Int = 0
    private lateinit var mAdapter: ProjectListAdapter

    fun newInstance(bundle: Bundle): ProjectListFragment {
        val fragment = ProjectListFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun createPresenter(): ProjectListContract.Presenter = ProjectListPresenter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_project_list
    }

    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.setHasFixedSize(true)

        val articleList = mutableListOf<Article>()
        mAdapter = ProjectListAdapter(R.layout.item_home_article_list, articleList)
        mRecyclerView.adapter = mAdapter
    }

    override fun initEventAndData() {
        arguments?.let {
            cid = it.getInt("cid")
        }

        mPresenter?.refresh(cid)

        mAdapter.setOnItemClickListener { _, _, position ->
            if (mAdapter.data.size <= 0 || mAdapter.data.size < position) {
                return@setOnItemClickListener
            }

            val intent = Intent(activity, ArticleDetailActivity::class.java)
            intent.putExtra("title", mAdapter.data[position].title)
            intent.putExtra("articleLink", mAdapter.data[position].link)
            intent.putExtra("articleId", mAdapter.data[position].id)
            intent.putExtra("isCollected", mAdapter.data[position].collect)
            intent.putExtra("isShowCollectIcon", true)
            intent.putExtra("articleItemPosition", position)
            context?.startActivity(intent)
        }

        initRefreshLayout()
    }

    private fun initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener { refreshLayout ->
            mPresenter?.refresh(cid)
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

    override fun getProjectListSuccess(articles: MutableList<Article>, isFresh: Boolean) {
        if (isFresh) {
            mAdapter.replaceData(articles)
        } else {
            mAdapter.addData(articles)
        }
    }

    override fun getProjectListError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }
}