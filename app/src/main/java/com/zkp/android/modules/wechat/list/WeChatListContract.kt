package com.zkp.android.modules.wechat.list

import com.zkp.android.base.model.IModel
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView
import com.zkp.android.bean.Article
import com.zkp.android.bean.ArticleResponseBody
import com.zkp.android.bean.HttpResult
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.wechat.list
 * @time: 2019/5/30 10:24
 * @description:
 */
class WeChatListContract {

    interface View : IView {

        /**
         * 获取某个微信公众号文章列表成功
         * @param articles
         * @param isFresh
         */
        fun getWeChatArticleSuccess(articles: MutableList<Article>, isFresh: Boolean)

        /**
         * 获取某个微信公众号的文章列表失败
         * @param errorMsg
         */
        fun getWeChatArticleError(errorMsg: String)

    }

    interface Presenter : IPresenter<View> {

        /**
         * 获取某个微信公众号的文章列表
         * @param id 微信公众号id
         * @param page 页码 从1开始
         * @param isFresh 是否刷新
         */
        fun getWeChatArticle(id: Int, page: Int, isFresh: Boolean)

        /**
         * 刷新页面数据
         */
        fun refresh(id: Int)

        /**
         * 加载更多
         */
        fun loadMore()
    }

    interface Model : IModel {

        /**
         * 请求某个微信公众号下的文章列表
         * @param id 微信公众号id
         * @param page 页码 从1开始
         */
        fun requestWeChatArticle(id: Int, page: Int): Observable<HttpResult<ArticleResponseBody>>

    }

}