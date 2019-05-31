package com.zkp.android.modules.home.detail

import android.content.Intent
import android.net.Uri
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.Toolbar
import android.text.Html
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import butterknife.BindView
import com.coder.zzq.smartshow.toast.SmartToast
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.NestedScrollAgentWebView
import com.zkp.android.R
import com.zkp.android.base.activity.BaseActivity
import com.zkp.android.bean.CollectArticle
import com.zkp.android.bean.CollectResponseBody
import com.zkp.android.http.AppConfig

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.home.detail
 * @time: 2019/5/29 10:41
 * @description:
 */
class ArticleDetailActivity : BaseActivity<ArticleDetailContract.View, ArticleDetailContract.Presenter>(),
    ArticleDetailContract.View {

    @BindView(R.id.content_layout)
    lateinit var mContent: CoordinatorLayout

    @BindView(R.id.toolbar)
    lateinit var mToolbar: Toolbar

    @BindView(R.id.toolbar_title)
    lateinit var mTitle: TextView

    /**
     * 文章ID
     */
    private var articleId: Int = 0
    /**
     * 文章Url
     */
    private var articleLink: String? = null
    /**
     * 标题
     */
    private var title: String? = null
    /**
     * 是否收藏
     */
    private var isCollected: Boolean = false
    /**
     * 是否显示收藏icon
     */
    private var isShowCollectIcon: Boolean = false
    /**
     * 是否为测试博客
     */
    private var isCnBlog = false
    private var page = 0

    private lateinit var mAgentWeb: AgentWeb
    private lateinit var mCollectItem: MenuItem

    override fun createPresenter(): ArticleDetailContract.Presenter = ArticleDetailPresenter()

    override fun getLayoutId(): Int {
        return R.layout.activity_article_detail
    }

    override fun initToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        if (!TextUtils.isEmpty(title)) {
            mTitle.text = Html.fromHtml(title)
        }
        mTitle.isSelected = true
        mToolbar.setNavigationOnClickListener { onBackPressedSupport() }
    }

    override fun initView() {

    }

    override fun initEventAndData() {
        getBundleData()

        val webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                mTitle.text = Html.fromHtml(title)
                if (isCnBlog) {
                    mCollectItem.setIcon(R.drawable.ic_like_not_white)
                    page = 0
                    mPresenter?.getCollectList(page)
                }
            }
        }

        val webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                articleLink = url
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                articleLink = request.url.toString()
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        val layoutParams = CoordinatorLayout.LayoutParams(-1, -1)
        layoutParams.behavior = AppBarLayout.ScrollingViewBehavior()
        val mNestedWebView = NestedScrollAgentWebView(this)
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(mContent, layoutParams)
            .useDefaultIndicator()
            .setWebView(mNestedWebView)
            .setWebChromeClient(webChromeClient)
            .setWebViewClient(webViewClient)
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
            .createAgentWeb()
            .ready()
            .go(articleLink)

    }

    private fun getBundleData() {
        intent.extras?.let {
            title = it.getString("title")
            articleLink = it.getString("articleLink")
            articleId = it.getInt("articleId")
            isCnBlog = it.getBoolean("isCnBlog")
            isCollected = it.getBoolean("isCollected")
            isShowCollectIcon = it.getBoolean("isShowCollectIcon")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_acticle_detail, menu)
        mCollectItem = menu!!.findItem(R.id.item_collect)
        mCollectItem.isVisible = isShowCollectIcon
        mCollectItem.setIcon(if (isCollected) R.drawable.ic_like_white else R.drawable.ic_like_not_white)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_share -> {
                SmartToast.show(R.string.share)
            }
            R.id.item_collect -> {
                if (isCollected) {
                    mPresenter?.unCollectArticle(articleId)
                } else {
                    if (isCnBlog) {
                        mPresenter?.collectOutArticle(mTitle.text.toString(), AppConfig().CNBLOGS_AUTHOR, articleLink!!)
                        isCnBlog = false
                    } else {
                        mPresenter?.collectArticle(articleId)
                    }
                }
                isCollected = !isCollected
            }
            R.id.item_system_browser -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(articleLink)))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMenuOpened(featureId: Int, menu: Menu?): Boolean {
        if ("MenuBuilder".equals(menu?.javaClass?.simpleName, ignoreCase = true)) {
            val method = menu?.javaClass?.getDeclaredMethod("setOptionalIconsVisible", java.lang.Boolean.TYPE)
            method?.isAccessible = true
            method?.invoke(menu, true)
        }
        return super.onMenuOpened(featureId, menu)
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

    override fun onBackPressedSupport() {
        if (!mAgentWeb.back()) {
            super.onBackPressedSupport()
        }
    }

    override fun collectArticleSuccess() {
        mCollectItem.setIcon(if (isCollected) R.drawable.ic_like_white else R.drawable.ic_like_not_white)
    }

    override fun collectArticleError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun getCollectListSuccess(articles: CollectResponseBody<CollectArticle>) {
        for (datasBean in articles.datas) {
            if (datasBean.author == AppConfig().CNBLOGS_AUTHOR &&
                datasBean.title == mTitle.text.toString() &&
                datasBean.link == articleLink
            ) {
                //这篇文章已在收藏列表中
                isCollected = true
                mCollectItem.setIcon(R.drawable.ic_like_white)
                return
            }
        }

        if (!articles.over) {
            //收藏文章数>1页
            page++
            mPresenter?.getCollectList(page)
        }
    }

    override fun getCollectListError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun collectOutArticleSuccess() {
        mCollectItem.setIcon(if (isCollected) R.drawable.ic_like_white else R.drawable.ic_like_not_white)
    }

    override fun collectOutArticleError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun unCollectArticleSuccess() {
        mCollectItem.setIcon(if (isCollected) R.drawable.ic_like_white else R.drawable.ic_like_not_white)
    }

    override fun unCollectArticleError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

}