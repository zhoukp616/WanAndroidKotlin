package com.zkp.android.modules.project

import com.zkp.android.base.model.IModel
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView
import com.zkp.android.bean.HttpResult
import com.zkp.android.bean.ProjectTree
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.project
 * @time: 2019/5/30 14:43
 * @description:
 */
class ProjectContract {

    interface View : IView {

        /**
         * 获取项目分类数据成功
         * @param projectTreeList
         */
        fun getProjectTreeSuccess(projectTreeList: MutableList<ProjectTree>)

        /**
         * 获取项目分类数据失败
         * @param errorMsg
         */
        fun getProjectTreeError(errorMsg: String)

    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取项目分类数据
         */
        fun getProjectTree()
    }

    interface Model : IModel {

        /**
         * 请求项目分类数据
         */
        fun requestProjectTree(): Observable<HttpResult<MutableList<ProjectTree>>>

    }

}