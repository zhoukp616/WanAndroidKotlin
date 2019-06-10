package com.zkp.android.modules.main

import com.zkp.android.base.model.IModel
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView
import com.zkp.android.bean.HttpResult
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main
 * @time: 2019/5/28 10:37
 * @description: MainActivity契约类
 */
interface MainContract {

    interface View : IView {

        fun logoutSuccess()

        fun logoutError(errorMsg: String)

    }

    interface Presenter : IPresenter<View> {

        /**
         * 是否是夜间模式
         *
         * @return
         */
        fun isNightMode(): Boolean

        /**
         * 设置夜间模式  true--夜间模式  false--日间模式
         *
         * @param isNightMode
         */
        fun setNightMode(isNightMode: Boolean)

        /**
         * 退出登录
         */
        fun logout()

    }

    interface Model : IModel {

        /**
         * 请求退出登录
         */
        fun requestLogout(): Observable<HttpResult<Any>>

    }

}