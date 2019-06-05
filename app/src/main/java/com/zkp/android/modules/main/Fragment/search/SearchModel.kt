package com.zkp.android.modules.main.Fragment.search

import com.zkp.android.app.App
import com.zkp.android.base.model.BaseModel
import com.zkp.android.bean.ArticleResponseBody
import com.zkp.android.bean.HttpResult
import com.zkp.android.http.ApiService
import com.zkp.android.http.AppConfig
import com.zkp.android.http.HttpsUtil
import io.reactivex.Observable

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment.search
 * @time: 2019/6/4 16:54
 * @description:
 */
class SearchModel : BaseModel(), SearchContract.Model {
    override fun requestSearchArticleByKeyWord(page: Int, k: String): Observable<HttpResult<ArticleResponseBody>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java)
            .searchArticlesByKeyWord(page, k)
    }

    override fun requestCollectArticle(id: Int): Observable<HttpResult<Any>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java).collectArticle(id)
    }

    override fun requestUnCollectArticle(id: Int): Observable<HttpResult<Any>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java)
            .unCollectArticle(id)
    }
}