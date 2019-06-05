package com.zkp.android.modules.main.fragment.usage

import com.zkp.android.base.presenter.BasePresenter
import com.zkp.android.bean.Friend
import com.zkp.android.bean.HttpResult
import com.zkp.android.http.HttpsUtil

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment.usage
 * @time: 2019/5/31 17:31
 * @description:
 */
class UseagePresenter : BasePresenter<UsegeContract.Model, UsegeContract.View>(), UsegeContract.Presenter {

    override fun createModel(): UsegeContract.Model? = UseageModel()

    override fun getFriendJson() {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestFrienJson(),
            object : HttpsUtil.IResponseListener<HttpResult<MutableList<Friend>>> {
                override fun onSuccess(data: HttpResult<MutableList<Friend>>) {
                    if (data.errorCode == 0) {
                        mView?.getFriendJsonSuccess(data.data)
                    } else {
                        mView?.getFriendJsonError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.getFriendJsonError(errMsg)
                    mView?.hideLoading()
                }

            })
    }
}