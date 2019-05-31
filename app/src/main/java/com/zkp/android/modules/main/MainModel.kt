package com.zkp.android.modules.main

import com.zkp.android.app.App
import com.zkp.android.base.model.BaseModel
import com.zkp.android.bean.HttpResult
import com.zkp.android.http.ApiService
import com.zkp.android.http.AppConfig
import com.zkp.android.http.HttpsUtil
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main
 * @time: 2019/5/28 10:39
 * @description:
 */
class MainModel : BaseModel(), MainContract.Model {
    override fun requestLogout(): Observable<HttpResult<Any>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java).logout()
    }
}