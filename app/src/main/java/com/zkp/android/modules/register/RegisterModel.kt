package com.zkp.android.modules.register

import com.zkp.android.app.App
import com.zkp.android.base.model.BaseModel
import com.zkp.android.bean.HttpResult
import com.zkp.android.bean.Login
import com.zkp.android.http.ApiService
import com.zkp.android.http.AppConfig
import com.zkp.android.http.HttpsUtil
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.register
 * @time: 2019/5/31 10:51
 * @description:
 */
class RegisterModel : BaseModel(), RegisterContract.Model {
    override fun requestRegister(
        userName: String,
        password: String,
        rePassword: String
    ): Observable<HttpResult<Login>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java)
            .register(userName, password, rePassword)
    }
}