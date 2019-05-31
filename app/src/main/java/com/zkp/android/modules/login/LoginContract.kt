package com.zkp.android.modules.login

import com.zkp.android.base.model.IModel
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView
import com.zkp.android.bean.HttpResult
import com.zkp.android.bean.Login
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.login
 * @time: 2019/5/31 10:09
 * @description:
 */
class LoginContract {

    interface View : IView {

        /**
         * 登录成功
         * @param data
         */
        fun loginSuccess(data: Login)

        /**
         * 登录失败
         * @param errorMsg
         */
        fun loginError(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {
        /**
         * 登录
         * 登录后会在cookie中返回账号密码，只要在客户端做cookie持久化存储即可自动登录验证
         * @param userName 用户名
         * @param password 密码
         */
        fun login(userName: String, password: String)
    }

    interface Model : IModel {
        /**
         * 请求登录
         * 登录后会在cookie中返回账号密码，只要在客户端做cookie持久化存储即可自动登录验证
         * @param userName 用户名
         * @param password 密码
         */
        fun requestLogin(userName: String, password: String): Observable<HttpResult<Login>>
    }

}