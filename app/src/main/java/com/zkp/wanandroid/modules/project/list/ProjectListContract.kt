package com.zkp.wanandroid.modules.project.list

import com.zkp.wanandroid.base.model.IModel
import com.zkp.wanandroid.base.presenter.IPresenter
import com.zkp.wanandroid.base.view.IView
import com.zkp.wanandroid.bean.Article
import com.zkp.wanandroid.bean.ArticleResponseBody
import com.zkp.wanandroid.bean.HttpResult
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.project.list
 * @time: 2019/5/30 15:19
 * @description:
 */
class ProjectListContract {

    interface View : IView {

        /**
         * 获取某个分类下的项目列表成功
         * @param articles
         * @param isFresh 是否刷新
         */
        fun getProjectListSuccess(articles: MutableList<Article>, isFresh: Boolean)

        /**
         * 获取某个分类下的项目列表数据失败
         * @param errorMsg
         */
        fun getProjectListError(errorMsg: String)

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
         * 获取某个分类下的项目列表数据
         * @param page 页码 从1开始
         * @param cid 分类id
         * @param isFresh 是否刷新
         */
        fun getProjectList(page: Int, cid: Int, isFresh: Boolean)

        /**
         * 刷新页面数据
         * @param cid 分类id
         */
        fun refresh(cid: Int)

        /**
         * 加载更多
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
         * 请求某个分类下的项目列表数据
         */
        fun requestProjectList(page: Int, cid: Int): Observable<HttpResult<ArticleResponseBody>>

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