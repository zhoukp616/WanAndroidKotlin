package com.zkp.android.modules.main.Fragment.welfare

import com.zkp.android.base.model.BaseModel
import com.zkp.android.bean.HttpResultGank
import com.zkp.android.bean.WelFare
import com.zkp.android.http.ApiService
import com.zkp.android.http.AppConfig
import com.zkp.android.http.HttpUtil
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment.welfare
 * @time: 2019/6/3 9:21
 * @description:
 */
class WelfareModel : BaseModel(), WelFareContract.Model {
    override fun requestWelfare(page: Int): Observable<HttpResultGank<MutableList<WelFare>>> {
        return HttpUtil().createApi(AppConfig().BASE_URL_GANK, ApiService::class.java).getWelFare(page)
    }
}