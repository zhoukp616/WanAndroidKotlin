package com.zkp.android.modules.login

import com.zkp.android.base.presenter.BasePresenter
import com.zkp.android.bean.HttpResult
import com.zkp.android.bean.Login
import com.zkp.android.http.HttpsUtil

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.login
 * @time: 2019/5/31 10:11
 * @description:
 */
class LoginPresenter : BasePresenter<LoginContract.Model, LoginContract.View>(), LoginContract.Presenter {

    override fun createModel(): LoginContract.Model? = LoginModel()

    override fun login(userName: String, password: String) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestLogin(userName, password),
            object : HttpsUtil.IResponseListener<HttpResult<Login>> {
                override fun onSuccess(data: HttpResult<Login>) {
                    if (data.errorCode == 0) {
                        mView?.loginSuccess(data.data)
                    } else {
                        mView?.loginError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.loginError(errMsg)
                    mView?.hideLoading()
                }

            })
    }
}