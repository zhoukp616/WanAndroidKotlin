package com.zkp.android.modules.home.detail

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
 * @package: com.zkp.android.modules.home.detail
 * @time: 2019/5/29 10:43
 * @description:
 */
class ArticleDetailModel : BaseModel(), ArticleDetailContract.Model {

    override fun requestCollectList(page: Int): Observable<HttpResult<CollectResponseBody<CollectArticle>>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java)
            .getCollectList(page)
    }

    override fun requestCollectArticle(id: Int): Observable<HttpResult<Any>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java).collectArticle(id)
    }

    override fun requestUnCollectArticle(id: Int): Observable<HttpResult<Any>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java)
            .unCollectArticle(id)
    }

    override fun requestCollectOutArticle(title: String, author: String, link: String): Observable<HttpResult<Any>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java)
            .collectArticleOutside(title, author, link)
    }
}