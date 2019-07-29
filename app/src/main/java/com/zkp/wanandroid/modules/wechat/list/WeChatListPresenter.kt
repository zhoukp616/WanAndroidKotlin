package com.zkp.wanandroid.modules.wechat.list

import com.zkp.wanandroid.base.presenter.BasePresenter
import com.zkp.wanandroid.bean.ArticleResponseBody
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.http.HttpsUtil

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.wechat.list
 * @time: 2019/5/30 10:25
 * @description:
 */
class WeChatListPresenter : BasePresenter<WeChatListContract.Model, WeChatListContract.View>(),
    WeChatListContract.Presenter {

    private var id: Int = 0;
    private var currentPage: Int = 1

    override fun refresh(id: Int) {
        this.id = id
        this.currentPage = 1
        getWeChatArticle(id, currentPage, true)
    }

    override fun loadMore() {
        currentPage++
        getWeChatArticle(id, currentPage, false)
    }

    override fun createModel(): WeChatListContract.Model? = WeChatListModel()

    override fun getWeChatArticle(id: Int, page: Int, isFresh: Boolean) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestWeChatArticle(id, page),
            object : HttpsUtil.IResponseListener<HttpResult<ArticleResponseBody>> {

                override fun onSuccess(data: HttpResult<ArticleResponseBody>) {
                    if (data.errorCode == 0) {
                        mView?.getWeChatArticleSuccess(data.data.datas, isFresh)
                    } else {
                        mView?.getWeChatArticleError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.getWeChatArticleError(errMsg)
                    mView?.hideLoading()
                }
            })
    }

    override fun collectArticle(id: Int) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.collectArticle(id), object : HttpsUtil.IResponseListener<HttpResult<Any>> {
            override fun onSuccess(data: HttpResult<Any>) {
                if (data.errorCode == 0) {
                    mView?.collectArticleSuccess()
                } else {
                    mView?.collectArticleError(data.errorMsg)
                }
                mView?.hideLoading()
            }

            override fun onFail(errMsg: String) {
                mView?.collectArticleError(errMsg)
                mView?.hideLoading()
            }

        })
    }

    override fun unCollectArticle(id: Int) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.unCollectArticle(id), object : HttpsUtil.IResponseListener<HttpResult<Any>> {
            override fun onSuccess(data: HttpResult<Any>) {
                if (data.errorCode == 0) {
                    mView?.unCollectArticleSuccess()
                } else {
                    mView?.unCollectArticleError(data.errorMsg)
                }
                mView?.hideLoading()
            }

            override fun onFail(errMsg: String) {
                mView?.unCollectArticleError(errMsg)
                mView?.hideLoading()
            }

        })
    }

}