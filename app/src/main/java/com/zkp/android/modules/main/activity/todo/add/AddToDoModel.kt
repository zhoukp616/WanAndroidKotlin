package com.zkp.android.modules.main.activity.todo.add

import com.zkp.android.app.App
import com.zkp.android.base.model.BaseModel
import com.zkp.android.bean.HttpResult
import com.zkp.android.http.ApiService
import com.zkp.android.http.AppConfig
import com.zkp.android.http.HttpsUtil
import io.reactivex.Observable

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.todo.add
 * @time: 2019/6/3 16:33
 * @description:
 */
class AddToDoModel : BaseModel(), AddToDoContract.Model {
    override fun requestAddToDo(map: MutableMap<String, Any>): Observable<HttpResult<Any>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java)
            .addToDo(map)
    }

    override fun requestUpdateToDo(id: Int, map: MutableMap<String, Any>): Observable<HttpResult<Any>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java).updateTodo(id, map)
    }
}