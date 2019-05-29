package com.zkp.android.modules.knowledge.list

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
 * @package: com.zkp.android.modules.knowledge.list
 * @time: 2019/5/29 15:45
 * @description:
 */
class KnowledgeListContract {

    interface View : IView {

        /**
         * 获取知识体系下的文章列表成功
         * @param data
         * @param isFresh 是否刷新
         */
        fun getKonwledgeListSuccess(data: MutableList<Article>, isFresh: Boolean)

        /**
         * 获取知识体系下的文章列表失败
         * @param errorMsg
         */
        fun getKonwledgeListError(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取知识体系下的文章列表
         * @param page 页码 从0开始
         * @param cid 二级分类id
         * @param isFresh 是否刷新
         */
        fun getKonwledgeList(page: Int, cid: Int, isFresh: Boolean)

        /**
         * 刷新页面数据
         * @param cid
         */
        fun refresh(cid: Int)

        /**
         * 加载更多数据
         */
        fun loadMore()
    }

    interface Model : IModel {
        fun requestKonwledgeList(page: Int, cid: Int): Observable<HttpResult<ArticleResponseBody>>
    }

}