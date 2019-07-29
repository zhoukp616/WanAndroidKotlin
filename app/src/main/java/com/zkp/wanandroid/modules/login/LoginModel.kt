package com.zkp.wanandroid.modules.login

import com.zkp.wanandroid.app.App
import com.zkp.wanandroid.base.model.BaseModel
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.bean.Login
import com.zkp.wanandroid.http.ApiService
import com.zkp.wanandroid.http.AppConfig
import com.zkp.wanandroid.http.HttpsUtil
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