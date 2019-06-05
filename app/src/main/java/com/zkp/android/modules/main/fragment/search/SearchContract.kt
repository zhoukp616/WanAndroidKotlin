package com.zkp.android.modules.main.fragment.search

import com.zkp.android.base.model.IModel
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView
import com.zkp.android.bean.Article
import com.zkp.android.bean.ArticleResponseBody
import com.zkp.android.bean.HttpResult
import io.reactivex.Observable

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment.search
 * @time: 2019/6/4 16:53
 * @description:
 */
class SearchContract {

    interface View : IView {

        /**
         * 搜索文章成功
         * @param articleList
         * @param isFresh
         */
        fun searchArticlesByKeyWordSuccess(articleList: MutableList<Article>, isFresh: Boolean)

        /**
         * 搜索文章失败
         * @param errorMsg
         */
        fun searchArticlesByKeyWordError(errorMsg: String)

        /**
         * 收藏站内文章成功
         */
        fun collectArticleSuccess()

        /**
         * 收藏站内文章失败
         *
         * @param errMsg String
         */
        fun collectArticleError(errMsg: String)

        /**
         * 取消收藏站内文章成功
         */
        fun unCollectArticleSuccess()

        /**
         * 取消收藏站内文章失败
         *
         * @param errMsg String
         */
        fun unCollectArticleError(errMsg: String)

    }

    interface Presenter : IPresenter<View> {

        /**
         * 搜索文章
         * @param page 页码 从0开始
         * @param k 关键词
         * @param isFresh 是否刷新
         */
        fun searchArticlesByKeyWord(page: Int, k: String, isFresh: Boolean)

        /**
         * 刷新数据
         * @param k 关键词
         */
        fun refresh(k: String)

        /**
         * 加载更多
         */
        fun loadMore()

        /**
         * 收藏站内文章
         *
         * @param id
         */
        fun collectArticle(id: Int)

        /**
         * 取消收藏站内文章
         *
         * @param id
         */
        fun unCollectArticle(id: Int)

    }

    interface Model : IModel {

        /**
         * 请求搜索文章
         * @param page 页码 从0开始
         * @param k 关键词
         */
        fun requestSearchArticleByKeyWord(page: Int, k: String): Observable<HttpResult<ArticleResponseBody>>

        /**
         * 请求收藏文章
         * @param id 文章id
         */
        fun requestCollectArticle(id: Int): Observable<HttpResult<Any>>

        /**
         * 请求取消收藏站内文章
         */
        fun requestUnCollectArticle(id: Int): Observable<HttpResult<Any>>

    }

}