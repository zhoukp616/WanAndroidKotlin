package com.zkp.wanandroid.modules.main.fragment.collect

import com.zkp.wanandroid.base.presenter.BasePresenter
import com.zkp.wanandroid.bean.CollectArticle
import com.zkp.wanandroid.bean.CollectResponseBody
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.http.HttpsUtil

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment
 * @time: 2019/5/31 13:40
 * @description:
 */
class CollectPresenter : BasePresenter<CollectContract.Model, CollectContract.View>(),
    CollectContract.Presenter {

    private var currentPage: Int = 0

    override fun createModel(): CollectContract.Model? =
        CollectModel()

    override fun getCollectList(page: Int, isFresh: Boolean) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestCollectList(page),
            object : HttpsUtil.IResponseListener<HttpResult<CollectResponseBody<CollectArticle>>> {
                override fun onSuccess(data: HttpResult<CollectResponseBody<CollectArticle>>) {
                    if (data.errorCode == 0) {
                        mView?.getCollectListSuccess(data.data.datas, isFresh)
                    } else {
                        mView?.getCollectListError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.getCollectListError(errMsg)
                    mView?.hideLoading()
                }

            })
    }

    override fun refresh() {
        this.currentPage = 0
        getCollectList(currentPage, true)
    }

    override fun loadMore() {
        currentPage++
        getCollectList(currentPage, false)
    }

    override fun unCollectInCollectPage(id: Int, originId: Int) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestUnCollect(id, originId),
            object : HttpsUtil.IResponseListener<HttpResult<Any>> {
                override fun onSuccess(data: HttpResult<Any>) {
                    if (data.errorCode == 0) {
                        mView?.unCollectInCollectPageSuccess()
                    } else {
                        mView?.unCollectInCollectPageError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.unCollectInCollectPageError(errMsg)
                    mView?.hideLoading()
                }

            })
    }

}