package com.zkp.wanandroid.modules.main.fragment.collect

import com.zkp.wanandroid.app.App
import com.zkp.wanandroid.base.model.BaseModel
import com.zkp.wanandroid.bean.CollectArticle
import com.zkp.wanandroid.bean.CollectResponseBody
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.http.ApiService
import com.zkp.wanandroid.http.AppConfig
import com.zkp.wanandroid.http.HttpsUtil
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment
 * @time: 2019/5/31 13:38
 * @description:
 */
class CollectModel : BaseModel(), CollectContract.Model {
    override fun requestUnCollect(id: Int, originId: Int): Observable<HttpResult<Any>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java)
            .unCollectInCollectPage(id, originId)
    }

    override fun requestCollectList(page: Int): Observable<HttpResult<CollectResponseBody<CollectArticle>>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java)
            .getCollectList(page)
    }
}