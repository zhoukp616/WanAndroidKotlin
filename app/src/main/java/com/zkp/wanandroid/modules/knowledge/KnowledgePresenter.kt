package com.zkp.wanandroid.modules.knowledge

import com.zkp.wanandroid.base.presenter.BasePresenter
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.bean.KnowledgeTreeBody
import com.zkp.wanandroid.http.HttpsUtil

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.knowledge
 * @time: 2019/5/29 13:57
 * @description:
 */
class KnowledgePresenter : BasePresenter<KnowledgeContract.Model, KnowledgeContract.View>(),
    KnowledgeContract.Presenter {

    override fun createModel(): KnowledgeContract.Model? = KnowledgeModel()

    override fun getKnowledgeTree(isFresh: Boolean) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestKnowledgeTree(),
            object : HttpsUtil.IResponseListener<HttpResult<MutableList<KnowledgeTreeBody>>> {
                override fun onSuccess(data: HttpResult<MutableList<KnowledgeTreeBody>>) {
                    if (data.errorCode == 0) {
                        mView?.getKnowledgeTreeSuccess(data.data, isFresh)
                    } else {
                        mView?.getKnowledgeTreeError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.getKnowledgeTreeError(errMsg)
                    mView?.hideLoading()
                }

            })
    }

    override fun refresh() {
        getKnowledgeTree(true)
    }

    override fun loadMore() {
        getKnowledgeTree(false)
    }
}