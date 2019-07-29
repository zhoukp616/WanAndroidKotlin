package com.zkp.wanandroid.modules.main

import com.zkp.wanandroid.app.App
import com.zkp.wanandroid.base.presenter.BasePresenter
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.http.HttpsUtil
import com.zkp.wanandroid.utils.SpUtils

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main
 * @time: 2019/5/28 10:39
 * @description:
 */
class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>(), MainContract.Presenter {

    override fun createModel(): MainContract.Model? = MainModel()

    override fun isNightMode(): Boolean {
        return SpUtils().getBoolean(App.getContext(), "isNightMode")
    }

    override fun setNightMode(isNightMode: Boolean) {
        SpUtils().putBoolean(App.getContext(), "isNightMode", isNightMode)
    }

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