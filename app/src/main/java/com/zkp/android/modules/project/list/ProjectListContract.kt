package com.zkp.android.modules.project.list

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
    }

    interface Model : IModel {

        /**
         * 请求某个分类下的项目列表数据
         */
        fun requestProjectList(page: Int, cid: Int): Observable<HttpResult<ArticleResponseBody>>

    }

}