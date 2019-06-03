package com.zkp.android.modules.main.activity.todo.list

import com.zkp.android.base.model.IModel
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView
import com.zkp.android.bean.HttpResult
import com.zkp.android.bean.ToDo
import com.zkp.android.bean.ToDoResponseBody
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.todo.list
 * @time: 2019/6/3 11:33
 * @description:
 */
class ToDoListContract {

    interface View : IView {

        /**
         * 获取todo列表成功
         * @param todoList
         * @param isFresh 是否刷新
         */
        fun getToDoListSuccess(todoList: MutableList<ToDo>, isFresh: Boolean)

        /**
         * 获取TODO列表失败
         * @param errorMsg
         */
        fun getToDoListError(errorMsg: String)

    }

    interface Presenter : IPresenter<View> {

        /**
         * 获取todo列表
         *
         * @param page    页码
         * @param isFresh 是否刷新
         */
        fun getToDoList(page: Int, isFresh: Boolean)

        /**
         * 刷新todo列表
         *
         * @param type   类型
         * @param status 状态
         */
        fun refresh(type: Int, status: Int)

        /**
         * 加载更多
         */
        fun loadMore()

    }

    interface Model : IModel {

        /**
         * 请求TODO列表
         * @param page 页码 从1开始
         * @param map 参数列表
         */
        fun requestToDoList(page: Int, map: Map<String, Int>): Observable<HttpResult<ToDoResponseBody>>

    }

}