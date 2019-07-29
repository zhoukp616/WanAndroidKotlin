package com.zkp.wanandroid.base.fragment

import android.os.Bundle
import android.view.View
import com.classic.common.MultipleStatusView
import com.sunchen.netbus.NetStatusBus
import com.sunchen.netbus.annotation.NetSubscribe
import com.sunchen.netbus.type.Mode
import com.zkp.wanandroid.R
import com.zkp.wanandroid.base.presenter.IPresenter
import com.zkp.wanandroid.base.view.IView
import javax.inject.Inject

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.base.fragment
 * @time: 2019/5/27 15:44
 * @description:
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<in V : IView, P : IPresenter<V>> : AbstractSimpleFragment(), IView {

    @Inject
    protected var mPresenter: P? = null
    private var mMultipleStatusView: MultipleStatusView? = null

    protected abstract fun createPresenter(): P

    override fun onStart() {
        super.onStart()
        NetStatusBus.getInstance().register(this)
    }

    override fun onStop() {
        super.onStop()
        NetStatusBus.getInstance().unregister(this)
    }

    /**
     * 只有当网络丢失时，该类型订阅者才会被回调
     */
    @NetSubscribe(mode = Mode.NONE)
    fun noNetWork() {
        showNoNetwork()
    }

    /**
     * 只有移动网络连接时，该类型订阅者才会被回调
     */
    @NetSubscribe(mode = Mode.MOBILE_CONNECT)
    fun mobileConnect() {
        showContent()
    }

    /**
     * 只有wifi连接时，该类型订阅者才会被回调
     */
    @NetSubscribe(mode = Mode.WIFI_CONNECT)
    fun wifiConnect() {
        showContent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)

        mMultipleStatusView = view.findViewById(R.id.custom_multiple_status_view)
        mMultipleStatusView?.setOnRetryClickListener { mPresenter?.reload() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detachView()
        mPresenter = null
    }

    override fun showLoading() {
        mMultipleStatusView?.showLoading()
    }

    override fun hideLoading() {
        mMultipleStatusView?.showContent()
    }

    override fun showError() {
        mMultipleStatusView?.showError()
    }

    override fun showNoNetwork() {
        mMultipleStatusView?.showNoNetwork()
    }

    override fun showEmpty() {
        mMultipleStatusView?.showEmpty()
    }

    override fun showContent() {
        mMultipleStatusView?.showContent()
    }

}