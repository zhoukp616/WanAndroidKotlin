package com.zkp.android.modules.navigation

import com.zkp.android.app.App
import com.zkp.android.base.model.BaseModel
import com.zkp.android.bean.HttpResult
import com.zkp.android.bean.Navigation
import com.zkp.android.http.ApiService
import com.zkp.android.http.AppConfig
import com.zkp.android.http.HttpsUtil
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.navigation
 * @time: 2019/5/30 11:36
 * @description:
 */
class NavigationModel : BaseModel(), NavigationContract.Model {

    override fun requestNaviJson(): Observable<HttpResult<MutableList<Navigation>>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java).getNaviJson()
    }
}