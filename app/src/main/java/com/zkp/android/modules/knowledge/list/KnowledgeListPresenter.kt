package com.zkp.android.modules.knowledge.list

import com.zkp.android.base.presenter.BasePresenter
import com.zkp.android.bean.ArticleResponseBody
import com.zkp.android.bean.HttpResult
import com.zkp.android.http.HttpsUtil

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.knowledge.list
 * @time: 2019/5/29 15:47
 * @description:
 */
class KnowledgeListPresenter : BasePresenter<KnowledgeListContract.Model, KnowledgeListContract.View>(),
    KnowledgeListContract.Presenter {

    private var currentPage: Int = 0
    private var cid: Int = 0

    override fun createModel(): KnowledgeListContract.Model?  = KnowledgeListModel()

    override fun refresh(cid: Int) {
        this.cid = cid
        this.currentPage = 0
        getKonwledgeList(currentPage, cid, true)
    }

    override fun loadMore() {
        currentPage++
        getKonwledgeList(currentPage, cid, false)
    }

    override fun getKonwledgeList(page: Int, cid: Int, isFresh: Boolean) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestKonwledgeList(page, cid),
            object : HttpsUtil.IResponseListener<HttpResult<ArticleResponseBody>> {
                override fun onSuccess(data: HttpResult<ArticleResponseBody>) {
                    if (data.errorCode == 0) {
                        mView?.getKonwledgeListSuccess(data.data.datas, isFresh)
                    } else {
                        mView?.getKonwledgeListError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.getKonwledgeListError(errMsg)
                    mView?.hideLoading()
                }

            })
    }
}