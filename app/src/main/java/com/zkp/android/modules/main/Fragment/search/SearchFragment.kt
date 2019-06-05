package com.zkp.android.modules.main.Fragment.search

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
import com.zkp.android.modules.home.adapter.HomeArticleAdapter
import com.zkp.android.modules.home.detail.ArticleDetailActivity

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment.search
 * @time: 2019/6/4 16:52
 * @description:
 */
class SearchFragment : BaseFragment<SearchContract.View, SearchContract.Presenter>(), SearchContract.View {

    @BindView(R.id.refreshLayout)
    lateinit var mRefreshLayout: SmartRefreshLayout

    @BindView(R.id.recyclerView)
    lateinit var mRecyclerView: RecyclerView

    private var mKeyWord = ""

    private lateinit var mAdapter: HomeArticleAdapter

    fun newInstance(bundle: Bundle): SearchFragment {
        val fragment = SearchFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun createPresenter(): SearchContract.Presenter = SearchPresenter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_search
    }

    override fun initView() {

        mRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.setHasFixedSize(true)

        val articleList = mutableListOf<Article>()
        mAdapter = HomeArticleAdapter(R.layout.item_home_article_list, articleList)
        mRecyclerView.adapter = mAdapter

    }

    override fun initEventAndData() {
        mKeyWord = arguments?.getString("search_key", "")!!

        initRefreshLayout()

        mPresenter?.refresh(mKeyWord)

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
            activity?.startActivity(intent)
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

    }

    private fun initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener { refreshLayout ->
            mPresenter?.refresh(mKeyWord)
            refreshLayout.finishRefresh()
        }
        mRefreshLayout.setOnLoadMoreListener { refreshLayout ->
            mPresenter?.loadMore()
            refreshLayout.finishLoadMore()
        }
    }

    override fun searchArticlesByKeyWordSuccess(articleList: MutableList<Article>, isFresh: Boolean) {
        if (isFresh) {
            mAdapter.replaceData(articleList)
        } else {
            mAdapter.addData(articleList)
        }
    }

    override fun searchArticlesByKeyWordError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun collectArticleSuccess() {
        mPresenter?.refresh(mKeyWord)
        SmartToast.show("收藏成功")
    }

    override fun collectArticleError(errMsg: String) {
        SmartToast.show(errMsg)
    }

    override fun unCollectArticleSuccess() {
        mPresenter?.refresh(mKeyWord)
        SmartToast.show("取消收藏成功")
    }

    override fun unCollectArticleError(errMsg: String) {
        SmartToast.show(errMsg)
    }

}