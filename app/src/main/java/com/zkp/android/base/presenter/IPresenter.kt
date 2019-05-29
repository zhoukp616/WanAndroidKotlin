package com.zkp.android.base.presenter

import com.zkp.android.base.view.IView

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
}