package com.zkp.android.modules.login

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
 * @package: com.zkp.android.modules.login
 * @time: 2019/5/31 10:11
 * @description:
 */
class LoginModel : BaseModel(), LoginContract.Model {
    override fun requestLogin(userName: String, password: String): Observable<HttpResult<Login>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java)
            .login(userName, password)
    }
}