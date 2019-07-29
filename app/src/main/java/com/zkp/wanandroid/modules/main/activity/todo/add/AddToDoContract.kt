package com.zkp.wanandroid.modules.main.activity.todo.add

import com.zkp.wanandroid.base.model.IModel
import com.zkp.wanandroid.base.presenter.IPresenter
import com.zkp.wanandroid.base.view.IView
import com.zkp.wanandroid.bean.HttpResult
import io.reactivex.Observable

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.todo.add
 * @time: 2019/6/3 16:32
 * @description:
 */
class AddToDoContract {

    interface View : IView {

        /**
         * 添加一条TODO数据成功
         */
        fun addToDoSuccess()

        /**
         * 添加一条TODO数据失败
         * @param errorMsg
         */
        fun addToDoError(errorMsg: String)

        /**
         * 更新一条TODO数据成功
         */
        fun updateToDoSuccess()

        /**
         * 更新一条TODO数据失败
         * @param errorMsg
         */
        fun updateToDoError(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {

        /**
         * 添加一条TODO数据
         * title: 新增标题
         * content: 新增详情
         * date: 2018-08-01
         * type: 0
         */
        fun addToDo(map: MutableMap<String, Any>)

        /**
         * 修改一条TODO数据
         * title: 新增标题
         * content: 新增详情
         * date: 2018-08-01
         * type: 0
         */
        fun updateToDo(id: Int, map: MutableMap<String, Any>)

    }

    interface Model : IModel {

        /**
         * 请求添加一条TODO数据
         * title: 新增标题
         * content: 新增详情
         * date: 2018-08-01
         * type: 0
         *
         */
        fun requestAddToDo(map: MutableMap<String, Any>): Observable<HttpResult<Any>>

        /**
         * 请求修改一条TODO数据
         * title: 新增标题
         * content: 新增详情
         * date: 2018-08-01
         * type: 0
         *
         */
        fun requestUpdateToDo(id: Int, map: MutableMap<String, Any>): Observable<HttpResult<Any>>

    }

}