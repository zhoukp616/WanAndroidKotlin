package com.zkp.android.modules.home.detail

import com.zkp.android.base.model.IModel
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView
import com.zkp.android.bean.CollectArticle
import com.zkp.android.bean.CollectResponseBody
import com.zkp.android.bean.HttpResult
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.home.detail
 * @time: 2019/5/29 10:42
 * @description:
 */
class ArticleDetailContract {

    interface View : IView {

        /**
         * 收藏站内文章成功
         */
        fun collectArticleSuccess()

        /**
         * 收藏站内文章失败
         *
         * @param errorMsg String
         */
        fun collectArticleError(errorMsg: String)

        /**
         * 获取收藏列表成功
         *
         * @param articles    CollectListBean
         */
        fun getCollectListSuccess(articles: CollectResponseBody<CollectArticle>)

        /**
         * 获取收藏列表失败
         *
         * @param errorMsg String
         */
        fun getCollectListError(errorMsg: String)

        /**
         * 收藏站外文章成功
         */
        fun collectOutArticleSuccess()

        /**
         * 收藏站外文章失败
         *
         * @param errorMsg String
         */
        fun collectOutArticleError(errorMsg: String)

        /**
         * 取消收藏站内文章成功
         */
        fun unCollectArticleSuccess()

        /**
         * 取消收藏站内文章失败
         *
         * @param errorMsg String
         */
        fun unCollectArticleError(errorMsg: String)

    }

    interface Presenter : IPresenter<View> {
        /**
         * 收藏站内文章
         *
         * @param id
         */
        fun collectArticle(id: Int)

        /**
         * 获取收藏列表
         *
         * @param page    int
         */
        fun getCollectList(page: Int)

        /**
         * 收藏站外文章
         *
         * @param title  标题
         * @param author 作者
         * @param link   连接
         */
        fun collectOutArticle(title: String, author: String, link: String)

        /**
         * 取消收藏站内文章
         *
         * @param id
         */
        fun unCollectArticle(id: Int)
    }

    interface Model : IModel {

        /**
         * 请求收藏文章列表
         * @param page 页码 从0开始
         */
        fun requestCollectList(page: Int): Observable<HttpResult<CollectResponseBody<CollectArticle>>>

        /**
         * 请求收藏文章
         * @param id 文章id
         */
        fun requestCollectArticle(id: Int): Observable<HttpResult<Any>>

        /**
         * 请求取消收藏站内文章
         */
        fun requestUnCollectArticle(id: Int): Observable<HttpResult<Any>>

        /**
         * 请求收藏站外文章
         * @param title  标题
         * @param author 作者
         * @param link   连接
         */
        fun requestCollectOutArticle(title: String, author: String, link: String): Observable<HttpResult<Any>>

    }

}