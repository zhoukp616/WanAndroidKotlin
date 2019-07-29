package com.zkp.wanandroid.modules.main.activity.todo.add

import com.zkp.wanandroid.base.presenter.BasePresenter
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.http.HttpsUtil

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.todo.add
 * @time: 2019/6/3 16:34
 * @description:
 */
class AddToDoPresenter : BasePresenter<AddToDoContract.Model, AddToDoContract.View>(), AddToDoContract.Presenter {
    override fun createModel(): AddToDoContract.Model? = AddToDoModel()

    override fun addToDo(map: MutableMap<String, Any>) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestAddToDo(map),
            object : HttpsUtil.IResponseListener<HttpResult<Any>> {
                override fun onSuccess(data: HttpResult<Any>) {
                    if (data.errorCode == 0) {
                        mView?.addToDoSuccess()
                    } else {
                        mView?.addToDoError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.addToDoError(errMsg)
                    mView?.hideLoading()
                }

            })
    }

    override fun updateToDo(id: Int, map: MutableMap<String, Any>) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestUpdateToDo(id, map),
            object : HttpsUtil.IResponseListener<HttpResult<Any>> {
                override fun onSuccess(data: HttpResult<Any>) {
                    if (data.errorCode == 0) {
                        mView?.updateToDoSuccess()
                    } else {
                        mView?.updateToDoError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.updateToDoError(errMsg)
                    mView?.hideLoading()
                }

            })
    }
}
