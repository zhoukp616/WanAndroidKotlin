package com.zkp.wanandroid.modules.main.activity.todo.list

import com.zkp.wanandroid.base.presenter.BasePresenter
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.bean.ToDoResponseBody
import com.zkp.wanandroid.http.AppConfig
import com.zkp.wanandroid.http.HttpsUtil
import java.util.HashMap

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.todo.list
 * @time: 2019/6/3 11:35
 * @description:
 */
class ToDoListPresenter : BasePresenter<ToDoListContract.Model, ToDoListContract.View>(), ToDoListContract.Presenter {

    private var type: Int = 0
    private var status: Int = 0
    private var currentPage = 1


    override fun createModel(): ToDoListContract.Model? = ToDoListModel()

    override fun getToDoList(page: Int, isFresh: Boolean) {
        mView?.showLoading()

        val map = HashMap<String, Int>(4)
        map[AppConfig().KEY_TODO_TYPE] = type
        map[AppConfig().KEY_TODO_STATUS] = status
        //默认全部
        map[AppConfig().KEY_TODO_PRIORITY] = 0
        if (status == 1) {
            map[AppConfig().KEY_TODO_ORDERBY] = 2
        } else {
            map[AppConfig().KEY_TODO_ORDERBY] = 4
        }

        HttpsUtil().request(mModel!!.requestToDoList(page, map),
            object : HttpsUtil.IResponseListener<HttpResult<ToDoResponseBody>> {
                override fun onSuccess(data: HttpResult<ToDoResponseBody>) {
                    if (data.errorCode == 0) {
                        mView?.getToDoListSuccess(data.data.datas, isFresh)
                    } else {
                        mView?.getToDoListError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.getToDoListError(errMsg)
                    mView?.hideLoading()
                }

            })
    }

    override fun refresh(type: Int, status: Int) {
        this.type = type
        this.status = status
        currentPage = 1
        getToDoList(currentPage, true)
    }

    override fun loadMore() {
        currentPage++
        getToDoList(currentPage, false)
    }

    override fun deleteToDo(id: Int) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requsetDeleteToDo(id), object : HttpsUtil.IResponseListener<HttpResult<Any>> {
            override fun onSuccess(data: HttpResult<Any>) {
                if (data.errorCode == 0) {
                    mView?.deleteToDoSuccess()
                } else {
                    mView?.deleteToDoError(data.errorMsg)
                }
                mView?.hideLoading()
            }

            override fun onFail(errMsg: String) {
                mView?.getToDoListError(errMsg)
                mView?.hideLoading()
            }

        })
    }

    override fun updateToDoStatus(id: Int, status: Int) {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requsetUpdateToDoStatus(id, status),
            object : HttpsUtil.IResponseListener<HttpResult<Any>> {
                override fun onSuccess(data: HttpResult<Any>) {
                    if (data.errorCode == 0) {
                        mView?.updateToDoStatusSuccess()
                    } else {
                        mView?.updateToDoStatusError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.updateToDoStatusError(errMsg)
                    mView?.hideLoading()
                }

            })
    }

}