package com.zkp.wanandroid.modules.main.fragment.welfare

import com.zkp.wanandroid.base.model.BaseModel
import com.zkp.wanandroid.bean.HttpResultGank
import com.zkp.wanandroid.bean.WelFare
import com.zkp.wanandroid.http.ApiService
import com.zkp.wanandroid.http.AppConfig
import com.zkp.wanandroid.http.HttpUtil
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