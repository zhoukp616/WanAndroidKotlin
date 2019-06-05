package com.zkp.android.modules.main.fragment.collect

import com.zkp.android.app.App
import com.zkp.android.base.model.BaseModel
import com.zkp.android.bean.CollectArticle
import com.zkp.android.bean.CollectResponseBody
import com.zkp.android.bean.HttpResult
import com.zkp.android.http.ApiService
import com.zkp.android.http.AppConfig
import com.zkp.android.http.HttpsUtil
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