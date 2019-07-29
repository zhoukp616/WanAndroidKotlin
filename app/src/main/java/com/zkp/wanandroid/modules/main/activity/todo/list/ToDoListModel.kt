package com.zkp.wanandroid.modules.main.activity.todo.list

import com.zkp.wanandroid.app.App
import com.zkp.wanandroid.base.model.BaseModel
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.bean.ToDoResponseBody
import com.zkp.wanandroid.http.ApiService
import com.zkp.wanandroid.http.AppConfig
import com.zkp.wanandroid.http.HttpsUtil
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.todo.list
 * @time: 2019/6/3 11:34
 * @description:
 */
class ToDoListModel : BaseModel(), ToDoListContract.Model {
    override fun requestToDoList(page: Int, map: Map<String, Int>): Observable<HttpResult<ToDoResponseBody>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java)
            .getToDoList(page, map)
    }

    override fun requsetDeleteToDo(id: Int): Observable<HttpResult<Any>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java).deleteToDo(id)
    }

    override fun requsetUpdateToDoStatus(id: Int, status: Int): Observable<HttpResult<Any>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java)
            .updateToDoStatus(id, status)
    }
}