package com.zkp.android.modules.main.fragment.search

import com.zkp.android.base.presenter.BasePresenter
import com.zkp.android.bean.ArticleResponseBody
import com.zkp.android.bean.HttpResult
import com.zkp.android.http.HttpsUtil

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment.search
 * @time: 2019/6/4 16:55
 * @description:
 */
class SearchPresenter : BasePresenter<SearchContract.Model, SearchContract.View>(), SearchContract.Presenter {

    private var currentPag = 0
    private lateinit var k: String

    override fun createModel(): SearchContract.Model? = SearchModel()

    override fun searchArticlesByKeyWord(page: Int, k: String, isFresh: Boolean) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestSearchArticleByKeyWord(page, k),
            object : HttpsUtil.IResponseListener<HttpResult<ArticleResponseBody>> {
                override fun onSuccess(data: HttpResult<ArticleResponseBody>) {
                    if (data.errorCode == 0) {
                        mView?.searchArticlesByKeyWordSuccess(data.data.datas, isFresh)
                    } else {
                        mView?.searchArticlesByKeyWordError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.searchArticlesByKeyWordError(errMsg)
                    mView?.hideLoading()
                }

            })
    }

    override fun refresh(k: String) {
        this.currentPag = 0
        this.k = k
        searchArticlesByKeyWord(currentPag, k, true)
    }

    override fun loadMore() {
        currentPag++
        searchArticlesByKeyWord(currentPag, k, false)
    }

    override fun collectArticle(id: Int) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestCollectArticle(id), object : HttpsUtil.IResponseListener<HttpResult<Any>> {
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
        HttpsUtil().request(mModel!!.requestUnCollectArticle(id),
            object : HttpsUtil.IResponseListener<HttpResult<Any>> {
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