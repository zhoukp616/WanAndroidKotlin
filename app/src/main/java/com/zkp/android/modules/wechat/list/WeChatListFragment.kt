package com.zkp.android.modules.wechat.list

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
import com.zkp.android.modules.wechat.adapter.WeChatArticleAdapter

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.wechat.list
 * @time: 2019/5/30 10:23
 * @description:
 */
class WeChatListFragment : BaseFragment<WeChatListContract.View, WeChatListContract.Presenter>(),
    WeChatListContract.View {

    @BindView(R.id.refreshLayout)
    lateinit var mRefreshLayout: SmartRefreshLayout
    @BindView(R.id.recyclerView)
    lateinit var mRecyclerView: RecyclerView
    /**
     * 微信公众号ID
     */
    private var mId: Int = 0
    private lateinit var mAdapter: WeChatArticleAdapter

    override fun getWeChatArticleSuccess(articles: MutableList<Article>, isFresh: Boolean) {
        if (isFresh) {
            mAdapter.replaceData(articles)
        } else {
            mAdapter.addData(articles)
        }
    }

    override fun getWeChatArticleError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    fun newInstance(bundle: Bundle): WeChatListFragment {
        val fragment = WeChatListFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun createPresenter(): WeChatListContract.Presenter = WeChatListPresenter()
    override fun getLayoutId(): Int {
        return R.layout.fragment_wechat_list
    }

    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.setHasFixedSize(true)

        val articleList = mutableListOf<Article>()
        mAdapter = WeChatArticleAdapter(R.layout.item_home_article_list, articleList)
        mRecyclerView.adapter = mAdapter
    }

    override fun initEventAndData() {
        arguments?.let {
            mId = it.getInt("id")
        }

        initRefreshLayout()

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

        mPresenter?.refresh(mId)

    }

    private fun initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener { refreshLayout ->
            mPresenter?.refresh(mId)
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