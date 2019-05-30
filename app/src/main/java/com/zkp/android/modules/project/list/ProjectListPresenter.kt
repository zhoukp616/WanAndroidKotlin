package com.zkp.android.modules.project.list

import com.zkp.android.base.presenter.BasePresenter
import com.zkp.android.bean.ArticleResponseBody
import com.zkp.android.bean.HttpResult
import com.zkp.android.http.HttpsUtil

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.project.list
 * @time: 2019/5/30 15:21
 * @description:
 */
class ProjectListPresenter : BasePresenter<ProjectListContract.Model, ProjectListContract.View>(),
    ProjectListContract.Presenter {

    private var cid = 0
    private var currentPage: Int = 1


    override fun refresh(cid: Int) {
        this.cid = cid
        this.currentPage = 1
        getProjectList(currentPage, cid, true)
    }

    override fun loadMore() {
        currentPage++
        getProjectList(currentPage, cid, false)
    }

    override fun createModel(): ProjectListContract.Model? = ProjectListModel()

    override fun getProjectList(page: Int, cid: Int, isFresh: Boolean) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestProjectList(page, cid),
            object : HttpsUtil.IResponseListener<HttpResult<ArticleResponseBody>> {

                override fun onSuccess(data: HttpResult<ArticleResponseBody>) {
                    if (data.errorCode == 0) {
                        mView?.getProjectListSuccess(data.data.datas, isFresh)
                    } else {
                        mView?.getProjectListError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.getProjectListError(errMsg)
                    mView?.hideLoading()
                }

            })
    }
}