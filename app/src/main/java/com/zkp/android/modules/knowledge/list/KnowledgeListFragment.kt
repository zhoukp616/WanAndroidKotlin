package com.zkp.android.modules.knowledge.list

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import com.coder.zzq.smartshow.toast.SmartToast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zkp.android.R
import com.zkp.android.app.App
import com.zkp.android.base.fragment.BaseFragment
import com.zkp.android.bean.Article
import com.zkp.android.modules.home.detail.ArticleDetailActivity
import com.zkp.android.modules.knowledge.adapter.KnowledgeListAdapter

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.knowledge.list
 * @time: 2019/5/29 15:48
 * @description:
 */
class KnowledgeListFragment : BaseFragment<KnowledgeListContract.View, KnowledgeListContract.Presenter>(),
    KnowledgeListContract.View {

    @BindView(R.id.refreshLayout)
    lateinit var mRefreshLayout: SmartRefreshLayout

    @BindView(R.id.recyclerView)
    lateinit var mRecyclerView: RecyclerView
    /**
     * 分类的id，二级目录的id
     */
    private var cid: Int = 0
    private lateinit var mAdapter: KnowledgeListAdapter

    override fun getKonwledgeListSuccess(data: MutableList<Article>, isFresh: Boolean) {
        if (isFresh) {
            mAdapter.replaceData(data)
        } else {
            mAdapter.addData(data)
        }
    }

    override fun getKonwledgeListError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    fun newInstance(bundle: Bundle): KnowledgeListFragment {
        val fragment = KnowledgeListFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun createPresenter(): KnowledgeListContract.Presenter = KnowledgeListPresenter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_knowledge_list
    }

    override fun initView() {

        App().addFragment(this)

        mRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.setHasFixedSize(true)

        val articleList = mutableListOf<Article>()
        mAdapter = KnowledgeListAdapter(R.layout.item_home_article_list, articleList)
        mRecyclerView.adapter = mAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        App.mFragmentsList.remove(this)
    }

    override fun initEventAndData() {
        arguments?.let {
            cid = it.getInt("cid")
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

        mPresenter?.refresh(cid)

    }

    override fun collectArticleSuccess() {
        mPresenter?.refresh(cid)
    }

    override fun collectArticleError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun unCollectArticleSuccess() {
        mPresenter?.refresh(cid)
    }

    override fun unCollectArticleError(errorMsg: String) {
        SmartToast.show(errorMsg)
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
}