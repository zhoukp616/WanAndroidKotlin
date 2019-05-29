package com.zkp.android.base.activity

import com.classic.common.MultipleStatusView
import com.sunchen.netbus.NetStatusBus
import com.sunchen.netbus.annotation.NetSubscribe
import com.sunchen.netbus.type.Mode
import com.zkp.android.R
import com.zkp.android.base.presenter.BasePresenter
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView
import com.zkp.android.utils.CommonUtils
import javax.inject.Inject

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.base
 * @time: 2019/5/27 14:47
 * @description:
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<in V : IView, P : IPresenter<V>> : AbstractSimpleActivity(), IView {

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

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        this.mPresenter = null
    }

    override fun onViewCreated() {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
        mMultipleStatusView = findViewById(R.id.custom_multiple_status_view)
        mMultipleStatusView?.setOnRetryClickListener { mPresenter?.reload() }
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

    override fun onBackPressedSupport() {
        CommonUtils().hideKeyBoard(this, this.window.decorView.rootView)
        super.onBackPressedSupport()
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