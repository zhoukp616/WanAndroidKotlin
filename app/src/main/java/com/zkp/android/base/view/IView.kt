package com.zkp.android.base.view

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.base
 * @time: 2019/5/27 14:23
 * @description:
 */
interface IView {

    /**
     * 显示Loading
     */
    fun showLoading()

    /**
     * 隐藏Loading
     */
    fun hideLoading()

    /**
     * 显示errorView
     */
    fun showError()

    /**
     * 显示noNetworkView
     */
    fun showNoNetwork()

    /**
     * 显示emptyView
     */
    fun showEmpty()

    /**
     * 显示Content
     */
    fun showContent()

}