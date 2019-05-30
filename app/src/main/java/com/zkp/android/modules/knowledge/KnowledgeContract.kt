package com.zkp.android.modules.knowledge

import com.zkp.android.base.model.IModel
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView
import com.zkp.android.bean.HttpResult
import com.zkp.android.bean.KnowledgeTreeBody
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.knowledge
 * @time: 2019/5/29 13:54
 * @description:
 */
class KnowledgeContract {

    interface View : IView {
        /**
         * 获取知识体系数据成功
         * @param data
         * @param isFresh 是否刷新
         */
        fun getKnowledgeTreeSuccess(data: MutableList<KnowledgeTreeBody>, isFresh: Boolean)

        /**
         * 获取知识体系数据失败
         */
        fun getKnowledgeTreeError(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取知识体系数据
         * @param isFresh 是否刷新
         */
        fun getKnowledgeTree(isFresh: Boolean)

        /**
         * 刷新页面数据
         */
        fun refresh()

        /**
         * 加载更多数据
         */
        fun loadMore()
    }

    interface Model : IModel {
        /**
         * 请求知识体系数据
         */
        fun requestKnowledgeTree(): Observable<HttpResult<MutableList<KnowledgeTreeBody>>>
    }

}