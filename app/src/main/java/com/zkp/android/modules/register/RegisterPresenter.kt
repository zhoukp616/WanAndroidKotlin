package com.zkp.android.modules.register

import com.zkp.android.base.presenter.BasePresenter
import com.zkp.android.bean.HttpResult
import com.zkp.android.bean.Login
import com.zkp.android.http.HttpsUtil

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