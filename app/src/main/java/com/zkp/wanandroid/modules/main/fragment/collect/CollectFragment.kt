package com.zkp.wanandroid.modules.main.fragment.collect

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import com.coder.zzq.smartshow.toast.SmartToast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zkp.wanandroid.R
import com.zkp.wanandroid.base.fragment.BaseFragment
import com.zkp.wanandroid.bean.CollectArticle
import com.zkp.wanandroid.modules.home.detail.ArticleDetailActivity
import com.zkp.wanandroid.modules.main.fragment.adapter.CollectArticleAdapter

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment
 * @time: 2019/5/31 13:36
 * @description:
 */
class CollectFragment : BaseFragment<CollectContract.View, CollectContract.Presenter>(),
    CollectContract.View {

    @BindView(R.id.refreshLayout)
    lateinit var mRefreshLayout: SmartRefreshLayout

    @BindView(R.id.recyclerView)
    lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapter: CollectArticleAdapter

    override fun createPresenter(): CollectContract.Presenter =
        CollectPresenter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_collect
    }

    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.setHasFixedSize(true)

        val articleList = mutableListOf<CollectArticle>()
        mAdapter = CollectArticleAdapter(R.layout.item_collect_article_list, articleList)
        mRecyclerView.adapter = mAdapter

    }

    override fun initEventAndData() {
        initRefreshLayout()

        mAdapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(activity, ArticleDetailActivity::class.java)
            intent.putExtra("title", mAdapter.data[position].title)
            intent.putExtra("articleLink", mAdapter.data[position].link)
            intent.putExtra("articleId", mAdapter.data[position].id)
            intent.putExtra("isCollected", true)
            intent.putExtra("isShowCollectIcon", true)
            intent.putExtra("articleItemPosition", position)
            activity?.startActivity(intent)
        }

        mAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.ivArticleLike -> mPresenter?.unCollectInCollectPage(
                    mAdapter.getItem(position)?.id!!,
                    mAdapter.getItem(position)?.originId!!
                )
            }
        }

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

    override fun getCollectListSuccess(articles: MutableList<CollectArticle>, isFresh: Boolean) {
        if (isFresh) {
            mAdapter.replaceData(articles)
        } else {
            mAdapter.addData(articles)
        }
    }

    override fun getCollectListError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun unCollectInCollectPageSuccess() {
        mPresenter?.refresh()
    }

    override fun unCollectInCollectPageError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }
}