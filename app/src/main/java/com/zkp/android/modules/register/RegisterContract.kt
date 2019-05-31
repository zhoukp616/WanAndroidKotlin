package com.zkp.android.modules.register

import com.zkp.android.base.model.IModel
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView
import com.zkp.android.bean.HttpResult
import com.zkp.android.bean.Login
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.register
 * @time: 2019/5/31 10:51
 * @description:
 */
class RegisterContract {

    interface View : IView {

        /**
         * 注册成功
         * @param data
         */
        fun registerSuccess(data: Login)

        /**
         * 注册失败
         * @param errorMsg
         */
        fun registerError(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {

        /**
         * 注册
         * @param userName 用户名
         * @param password 密码
         * @param rePassword 重复密码
         */
        fun register(userName: String, password: String, rePassword: String)
    }

    interface Model : IModel {
        /**
         * 请求注册
         * @param userName 用户名
         * @param password 密码
         * @param rePassword 重复密码
         */
        fun requestRegister(userName: String, password: String, rePassword: String): Observable<HttpResult<Login>>
    }

}