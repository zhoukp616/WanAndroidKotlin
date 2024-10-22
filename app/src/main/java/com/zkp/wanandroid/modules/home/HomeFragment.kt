package com.zkp.wanandroid.modules.home

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import butterknife.BindView
import com.coder.zzq.smartshow.toast.SmartToast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.listener.OnBannerListener
import com.zkp.wanandroid.R
import com.zkp.wanandroid.app.App
import com.zkp.wanandroid.base.fragment.BaseFragment
import com.zkp.wanandroid.bean.Article
import com.zkp.wanandroid.modules.home.adapter.HomeArticleAdapter
import com.zkp.wanandroid.modules.home.detail.ArticleDetailActivity
import com.zkp.wanandroid.utils.GlideImageLoader
import java.util.*

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.home
 * @time: 2019/5/28 14:10
 * @description:
 */
class HomeFragment : BaseFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View, OnBannerListener {

    @BindView(R.id.refreshLayout)
    lateinit var mRefreshLayout: SmartRefreshLayout

    @BindView(R.id.recyclerView)
    lateinit var mRecyclerView: RecyclerView

    private var mAdapter: HomeArticleAdapter? = null
    private var mBanner: Banner? = null

    private var mImageUrlList: MutableList<String>? = null
    private var mUrlList: MutableList<String>? = null
    private var mIdList: MutableList<Int>? = null

    private var mTitles: MutableList<String>? = null

    fun getInstance(): HomeFragment {
        return HomeFragment()
    }

    override fun createPresenter(): HomeContract.Presenter = HomePresenter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    @SuppressLint("InflateParams")
    override fun initView() {

        App().addFragment(this)

        //设置布局管理器
        mRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //设置默认动画
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.setHasFixedSize(true)

        val articles = mutableListOf<Article>()
        mAdapter = HomeArticleAdapter(R.layout.item_home_article_list, articles)
        mRecyclerView.adapter = mAdapter

        val layout: LinearLayout = layoutInflater.inflate(R.layout.fragment_home_banner, null) as LinearLayout
        mBanner = layout.findViewById(R.id.banner)
        layout.removeView(mBanner)
        mAdapter?.setHeaderView(mBanner)

        //设置banner样式
        mBanner?.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        //设置图片加载器
        mBanner?.setImageLoader(GlideImageLoader())
        //设置banner动画效果
        mBanner?.setBannerAnimation(Transformer.Accordion)
        //设置自动轮播，默认为true
        mBanner?.isAutoPlay(true)
        //设置轮播时间
        mBanner?.setDelayTime(4000)
        //设置指示器位置(当banner模式中有指示器时)
        mBanner?.setIndicatorGravity(BannerConfig.CENTER)

    }

    override fun initEventAndData() {

        initRefreshLayout()

        mAdapter?.setOnItemClickListener { _, _, position ->
            if (mAdapter?.data?.size!! <= 0 || mAdapter?.data?.size!! < position) {
                return@setOnItemClickListener
            }

            val intent = Intent(activity, ArticleDetailActivity::class.java)
            intent.putExtra("title", mAdapter?.data!![position].title)
            intent.putExtra("articleLink", mAdapter?.data!![position].link)
            intent.putExtra("articleId", mAdapter?.data!![position].id)
            intent.putExtra("isCollected", mAdapter?.data!![position].collect)
            intent.putExtra("isShowCollectIcon", true)
            intent.putExtra("articleItemPosition", position)
            context?.startActivity(intent)
        }

        mAdapter?.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.ivArticleLike ->
                    //收藏
                    if (mAdapter?.getItem(position)?.collect!!) {
                        mPresenter?.unCollectArticle(mAdapter?.getItem(position)?.id!!)
                    } else {
                        mPresenter?.collectArticle(mAdapter?.getItem(position)?.id!!)
                    }
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

    override fun getBannerError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun getHomeArticleListSuccess(articles: MutableList<Article>, isFresh: Boolean) {
        if (isFresh) {
            mAdapter?.replaceData(articles)
            mPresenter?.getBanner()
        } else {
            mAdapter?.addData(articles)
        }
    }

    override fun getHomeArticleListError(errorMsg: String, isFresh: Boolean) {
        if (isFresh) {
            mPresenter?.getBanner()
        }
        SmartToast.show(errorMsg)
    }

    override fun getBannerSuccess(banners: MutableList<com.zkp.wanandroid.bean.Banner>) {
        if (mImageUrlList == null) {
            mImageUrlList = ArrayList()
            mIdList = ArrayList()
            mUrlList = ArrayList()
            mTitles = ArrayList()
        } else {
            mImageUrlList?.clear()
            mIdList?.clear()
            mUrlList?.clear()
            mTitles?.clear()
        }

        for (banner in banners.listIterator()) {
            mImageUrlList?.add(banner.imagePath)
            mIdList?.add(banner.id)
            mUrlList?.add(banner.url)
            mTitles?.add(banner.title)
        }

        //设置图片集合
        mBanner?.setImages(mImageUrlList)
        //设置banner标题
        mBanner?.setBannerTitles(mTitles)

        //banner设置方法全部调用完毕时最后调用
        mBanner?.start()

        mBanner?.setOnBannerListener(this)
    }

    override fun OnBannerClick(position: Int) {
        val intent = Intent(activity, ArticleDetailActivity::class.java)
        intent.putExtra("title", mTitles!![position])
        intent.putExtra("articleLink", mUrlList!![position])
        intent.putExtra("articleId", mTitles!![position])
        intent.putExtra("isCollected", false)
        intent.putExtra("isShowCollectIcon", true)
        intent.putExtra("articleItemPosition", position)
        context?.startActivity(intent)
    }

    override fun collectArticleSuccess() {
        mPresenter?.refresh()
    }

    override fun collectArticleError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun unCollectArticleSuccess() {
        mPresenter?.refresh()
    }

    override fun unCollectArticleError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            if (mBanner != null) {
                mBanner?.stopAutoPlay()
            }
        } else {
            if (mBanner != null) {
                mBanner?.startAutoPlay()
            }
        }
    }

    fun jumpToTop() {
        mRecyclerView.smoothScrollToPosition(0)
    }

}