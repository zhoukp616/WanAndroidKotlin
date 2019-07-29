package com.zkp.wanandroid.base.presenter

import com.zkp.wanandroid.base.view.IView

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.base
 * @time: 2019/5/27 14:24
 * @description:
 */
interface IPresenter<in V : IView> {
    /**
     * 绑定View
     *
     * @param mView V
     */
    fun attachView(mView: V)

    /**
     * 解绑View
     */
    fun detachView()

    /**
     * 重新加载
     */
    fun reload()

    /**
     * 注册EventBus
     */
    fun registerEventBus()

    /**
     * 取消注册EventBus
     */
    fun unregisterEventBus()

    /**
     * 获取登录状态
     *
     * @return true--已经登录   false--还未登录
     */
    fun getLoginStatus(): Boolean

    /**
     * 获取用户账号
     *
     * @return String
     */
    fun getUserAccount(): String

    /**
     * 设置用户账号
     *
     * @param userAccount String
     */
    fun setUserAccount(userAccount: String)
}