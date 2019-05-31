package com.zkp.android.modules.home

import com.zkp.android.base.model.IModel
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView
import com.zkp.android.bean.Article
import com.zkp.android.bean.ArticleResponseBody
import com.zkp.android.bean.Banner
import com.zkp.android.bean.HttpResult
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules
 * @time: 2019/5/28 14:06
 * @description: 首页契约类
 */
class HomeContract {

    interface View : IView {
        /**
         * 获取首页文章列表成功
         * @param articles
         * @param isFresh 是否刷新数据
         */
        fun getHomeArticleListSuccess(articles: MutableList<Article>, isFresh: Boolean)

        /**
         * 获取首页文章列表失败
         * @param errorMsg
         * @param isFresh
         */
        fun getHomeArticleListError(errorMsg: String, isFresh: Boolean)

        /**
         * 获取首页banner数据成功
         * @param banners
         */
        fun getBannerSuccess(banners: MutableList<Banner>)

        /**
         * 获取首页banner数据失败
         * @param errorMsg
         */
        fun getBannerError(errorMsg: String)

        /**
         * 收藏文章成功
         */
        fun collectArticleSuccess()

        /**
         * 收藏文章失败
         * @param errorMsg
         */
        fun collectArticleError(errorMsg: String)

        /**
         * 取消收藏文章成功
         */
        fun unCollectArticleSuccess()

        /**
         * 取消收藏文章失败
         */
        fun unCollectArticleError(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取首页文章列表
         * @param page 页码 从0开始
         * @param isFresh 是否刷新
         */
        fun getHomeArticleList(page: Int, isFresh: Boolean)

        /**
         * 获取首页轮播图
         */
        fun getBanner()

        /**
         * 刷新页面数据
         */
        fun refresh()

        /**
         * 加载更多数据
         */
        fun loadMore()

        /**
         * 收藏文章
         * @param id 文章id
         */
        fun collectArticle(id: Int)

        /**
         * 取消收藏文章
         * @param id 文章id
         */
        fun unCollectArticle(id: Int)

    }

    interface Model : IModel {
        /**
         * 请求banner数据
         */
        fun requestBanner(): Observable<HttpResult<MutableList<Banner>>>

        /**
         * 请求文章列表
         * @param page 从0开始
         */
        fun requestArticleList(page: Int): Observable<HttpResult<ArticleResponseBody>>

        /**
         * 收藏文章
         * @param id
         */
        fun collectArticle(id: Int): Observable<HttpResult<Any>>

        /**
         * 取消收藏文章
         * @param id
         */
        fun unCollectArticle(id: Int): Observable<HttpResult<Any>>
    }

}