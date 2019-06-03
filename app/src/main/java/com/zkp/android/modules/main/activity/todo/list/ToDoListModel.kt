package com.zkp.android.modules.main.activity.todo.list

import com.zkp.android.app.App
import com.zkp.android.base.model.BaseModel
import com.zkp.android.bean.HttpResult
import com.zkp.android.bean.ToDoResponseBody
import com.zkp.android.http.ApiService
import com.zkp.android.http.AppConfig
import com.zkp.android.http.HttpsUtil
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
}