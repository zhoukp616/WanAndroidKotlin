package com.zkp.wanandroid.modules.wechat.list

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import com.coder.zzq.smartshow.toast.SmartToast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zkp.wanandroid.R
import com.zkp.wanandroid.app.App
import com.zkp.wanandroid.base.fragment.BaseFragment
import com.zkp.wanandroid.bean.Article
import com.zkp.wanandroid.modules.home.detail.ArticleDetailActivity
import com.zkp.wanandroid.modules.wechat.adapter.WeChatArticleAdapter

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

        App().addFragment(this)

        mRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.setHasFixedSize(true)

        val articleList = mutableListOf<Article>()
        mAdapter = WeChatArticleAdapter(R.layout.item_home_article_list, articleList)
        mRecyclerView.adapter = mAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        App.mFragmentsList.remove(this)
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

        mAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.ivArticleLike ->
                    //收藏
                    if (mAdapter.getItem(position)?.collect!!) {
                        mPresenter?.unCollectArticle(mAdapter.getItem(position)?.id!!)
                    } else {
                        mPresenter?.collectArticle(mAdapter.getItem(position)?.id!!)
                    }
            }
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

    override fun collectArticleSuccess() {
        mPresenter?.refresh(mId)
    }

    override fun collectArticleError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun unCollectArticleSuccess() {
        mPresenter?.refresh(mId)
    }

    override fun unCollectArticleError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    fun jumpToTop() {
        mRecyclerView.smoothScrollToPosition(0)
    }

}