package com.zkp.wanandroid.modules.home.detail

import com.zkp.wanandroid.base.presenter.BasePresenter
import com.zkp.wanandroid.bean.CollectArticle
import com.zkp.wanandroid.bean.CollectResponseBody
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.http.HttpsUtil

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.home.detail
 * @time: 2019/5/29 10:44
 * @description:
 */
class ArticleDetailPresenter : BasePresenter<ArticleDetailContract.Model, ArticleDetailContract.View>(),
    ArticleDetailContract.Presenter {

    override fun createModel(): ArticleDetailContract.Model? = ArticleDetailModel()

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

    override fun getCollectList(page: Int) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestCollectList(page),
            object : HttpsUtil.IResponseListener<HttpResult<CollectResponseBody<CollectArticle>>> {
                override fun onSuccess(data: HttpResult<CollectResponseBody<CollectArticle>>) {
                    if (data.errorCode == 0) {
                        mView?.getCollectListSuccess(data.data)
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

    override fun collectOutArticle(title: String, author: String, link: String) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestCollectOutArticle(title, author, link),
            object : HttpsUtil.IResponseListener<HttpResult<Any>> {
                override fun onSuccess(data: HttpResult<Any>) {
                    if (data.errorCode == 0) {
                        mView?.collectOutArticleSuccess()
                    } else {
                        mView?.collectOutArticleError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.collectOutArticleError(errMsg)
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