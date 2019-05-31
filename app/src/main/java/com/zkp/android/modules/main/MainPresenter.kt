package com.zkp.android.modules.main

import com.zkp.android.app.App
import com.zkp.android.base.presenter.BasePresenter
import com.zkp.android.bean.HttpResult
import com.zkp.android.http.HttpsUtil
import com.zkp.android.utils.SpUtils

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main
 * @time: 2019/5/28 10:39
 * @description:
 */
class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>(), MainContract.Presenter {

    override fun createModel(): MainContract.Model? = MainModel()

    override fun logout() {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestLogout(), object : HttpsUtil.IResponseListener<HttpResult<Any>> {
            override fun onSuccess(data: HttpResult<Any>) {
                if (data.errorCode == 0) {
                    SpUtils().putBoolean(App.getContext(), "loginStatus", false)
                    SpUtils().putString(App.getContext(), "userAccount", "")
                    mView?.logoutSuccess()
                } else {
                    mView?.logoutError(data.errorMsg)
                }
                mView?.hideLoading()
            }

            override fun onFail(errMsg: String) {
                mView?.logoutError(errMsg)
                mView?.hideLoading()
            }

        })
    }
}