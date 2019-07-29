package com.zkp.wanandroid.modules.register

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