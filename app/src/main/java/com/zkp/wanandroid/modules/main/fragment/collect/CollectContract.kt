package com.zkp.wanandroid.modules.main.fragment.collect

import com.zkp.wanandroid.base.model.IModel
import com.zkp.wanandroid.base.presenter.IPresenter
import com.zkp.wanandroid.base.view.IView
import com.zkp.wanandroid.bean.*
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment
 * @time: 2019/5/31 13:37
 * @description:
 */
class CollectContract {

    interface View : IView {

        /**
         * 获取收藏的文章列表成功
         * @param articles
         * @param isFresh 是否刷新
         */
        fun getCollectListSuccess(articles: MutableList<CollectArticle>, isFresh: Boolean)

        /**
         * 获取收藏的文章列表失败
         * @param errorMsg
         */
        fun getCollectListError(errorMsg: String)

        /**
         * 取消收藏文章成功
         */
        fun unCollectInCollectPageSuccess()

        /**
         * 取消收藏文章失败
         * @param errorMsg
         */
        fun unCollectInCollectPageError(errorMsg: String)

    }

    interface Presenter : IPresenter<View> {

        /**
         * 获取收藏的文章列表
         * @param page 页码 从0开始
         * @param isFresh 是否刷新
         */
        fun getCollectList(page: Int, isFresh: Boolean)

        /**
         * 刷新
         */
        fun refresh()

        /**
         * 加载更多
         */
        fun loadMore()

        /**
         * 取消收藏文章
         * id:拼接在链接上
         * originId:列表页下发，无则为-1
         *
         * @param id       int
         * @param originId int
         */
        fun unCollectInCollectPage(id: Int, originId: Int)

    }

    interface Model : IModel {

        /**
         * 请求获取收藏的文章列表
         * @param page
         */
        fun requestCollectList(page: Int): Observable<HttpResult<CollectResponseBody<CollectArticle>>>

        /**
         * 请求取消收藏文章
         * id:拼接在链接上
         * originId:列表页下发，无则为-1
         * @param id
         * @param originId
         */
        fun requestUnCollect(id: Int, originId: Int): Observable<HttpResult<Any>>

    }

}