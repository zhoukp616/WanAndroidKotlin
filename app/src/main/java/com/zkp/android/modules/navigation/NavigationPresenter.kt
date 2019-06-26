package com.zkp.android.modules.navigation

import com.zkp.android.base.presenter.BasePresenter
import com.zkp.android.bean.HttpResult
import com.zkp.android.bean.Navigation
import com.zkp.android.http.HttpsUtil

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.navigation
 * @time: 2019/5/30 11:36
 * @description:
 */
class NavigationPresenter : BasePresenter<NavigationContract.Model, NavigationContract.View>(),
    NavigationContract.Presenter {

    override fun createModel(): NavigationContract.Model? = NavigationModel()

    override fun getNaviJson() {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestNaviJson(),
            object : HttpsUtil.IResponseListener<HttpResult<MutableList<Navigation>>> {
                override fun onSuccess(data: HttpResult<MutableList<Navigation>>) {
                    if (data.errorCode == 0) {
                        mView?.getNaviJsonSuccess(data.data)
                    } else {
                        mView?.getNaviJsonError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.getNaviJsonError(errMsg)
                    mView?.hideLoading()
                }

            })
    }

    override fun refresh() {
        getNaviJson()
    }
}