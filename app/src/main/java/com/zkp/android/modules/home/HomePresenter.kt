package com.zkp.android.modules.home

import com.zkp.android.base.presenter.BasePresenter
import com.zkp.android.bean.ArticleResponseBody
import com.zkp.android.bean.Banner
import com.zkp.android.bean.HttpResult
import com.zkp.android.http.HttpsUtil

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.home
 * @time: 2019/5/28 14:09
 * @description:
 */
class HomePresenter : BasePresenter<HomeContract.Model, HomeContract.View>(), HomeContract.Presenter {

    private var currentPage: Int = 1

    override fun createModel(): HomeContract.Model? = HomeModel()

    override fun getBanner() {
        mView?.showLoading()
        HttpsUtil().request(
            mModel!!.requestBanner(),
            object : HttpsUtil.IResponseListener<HttpResult<MutableList<Banner>>> {
                override fun onSuccess(data: HttpResult<MutableList<Banner>>) {
                    if (data.errorCode == 0) {
                        mView?.getBannerSuccess(data.data)
                    } else {
                        mView?.getBannerError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.getBannerError(errMsg)
                    mView?.hideLoading()
                }

            })
    }

    override fun getHomeArticleList(page: Int, isFresh: Boolean) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestArticleList(page),
            object : HttpsUtil.IResponseListener<HttpResult<ArticleResponseBody>> {
                override fun onSuccess(data: HttpResult<ArticleResponseBody>) {
                    if (data.errorCode == 0) {
                        mView?.getHomeArticleListSuccess(data.data.datas, isFresh)
                    } else {
                        mView?.getHomeArticleListError(data.errorMsg, isFresh)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.getHomeArticleListError(errMsg, isFresh)
                    mView?.hideLoading()
                }

            })
    }

    override fun refresh() {
        this.currentPage = 0
        getHomeArticleList(currentPage, true)
    }

    override fun loadMore() {
        this.currentPage++
        getHomeArticleList(currentPage, false)
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