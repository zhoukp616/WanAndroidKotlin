package com.zkp.wanandroid.modules.register

import com.zkp.wanandroid.base.presenter.BasePresenter
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.bean.Login
import com.zkp.wanandroid.http.HttpsUtil

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.register
 * @time: 2019/5/31 10:52
 * @description:
 */
class RegisterPresenter : BasePresenter<RegisterContract.Model, RegisterContract.View>(), RegisterContract.Presenter {

    override fun createModel(): RegisterContract.Model? = RegisterModel()

    override fun register(userName: String, password: String, rePassword: String) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestRegister(userName, password, rePassword),
            object : HttpsUtil.IResponseListener<HttpResult<Login>> {
                override fun onSuccess(data: HttpResult<Login>) {
                    if (data.errorCode == 0) {
                        mView?.registerSuccess(data.data)
                    } else {
                        mView?.registerError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.registerError(errMsg)
                    mView?.hideLoading()
                }

            })
    }
}