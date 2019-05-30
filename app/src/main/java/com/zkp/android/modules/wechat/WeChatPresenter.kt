package com.zkp.android.modules.wechat

import com.zkp.android.base.presenter.BasePresenter
import com.zkp.android.bean.HttpResult
import com.zkp.android.bean.ProjectTree
import com.zkp.android.http.HttpsUtil

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.wechat
 * @time: 2019/5/30 9:28
 * @description:
 */
class WeChatPresenter : BasePresenter<WeChatContract.Model, WeChatContract.View>(), WeChatContract.Presenter {

    override fun createModel(): WeChatContract.Model? = WeChatModel()

    override fun getWeChatChapters() {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestWeChatChaptrs(),
            object : HttpsUtil.IResponseListener<HttpResult<MutableList<ProjectTree>>> {

                override fun onSuccess(data: HttpResult<MutableList<ProjectTree>>) {
                    if (data.errorCode == 0) {
                        mView?.getWeChatChapterSuccess(data.data)
                    } else {
                        mView?.getWeChatChapterError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.getWeChatChapterError(errMsg)
                    mView?.hideLoading()
                }

            })
    }
}