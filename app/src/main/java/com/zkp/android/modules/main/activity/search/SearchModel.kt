package com.zkp.android.modules.main.activity.search

import com.zkp.android.app.App
import com.zkp.android.base.model.BaseModel
import com.zkp.android.bean.Hotkey
import com.zkp.android.bean.HttpResult
import com.zkp.android.http.ApiService
import com.zkp.android.http.AppConfig
import com.zkp.android.http.HttpsUtil
import io.reactivex.Observable

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.search
 * @time: 2019/6/4 14:41
 * @description:
 */
class SearchModel : BaseModel(), SearchContract.Model {
    override fun requestHotKeys(): Observable<HttpResult<MutableList<Hotkey>>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java).getHotKeys()
    }
}