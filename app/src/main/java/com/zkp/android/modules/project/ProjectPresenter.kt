package com.zkp.android.modules.project

import com.zkp.android.base.presenter.BasePresenter
import com.zkp.android.bean.HttpResult
import com.zkp.android.bean.ProjectTree
import com.zkp.android.http.HttpsUtil

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.project
 * @time: 2019/5/30 14:46
 * @description:
 */
class ProjectPresenter : BasePresenter<ProjectContract.Model, ProjectContract.View>(), ProjectContract.Presenter {

    override fun createModel(): ProjectContract.Model? = ProjectModel()

    override fun getProjectTree() {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestProjectTree(),
            object : HttpsUtil.IResponseListener<HttpResult<MutableList<ProjectTree>>> {

                override fun onSuccess(data: HttpResult<MutableList<ProjectTree>>) {
                    if (data.errorCode == 0) {
                        mView?.getProjectTreeSuccess(data.data)
                    } else {
                        mView?.getProjectTreeError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.getProjectTreeError(errMsg)
                    mView?.hideLoading()
                }

            })
    }

}