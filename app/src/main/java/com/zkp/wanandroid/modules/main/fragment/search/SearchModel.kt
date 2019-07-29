package com.zkp.wanandroid.modules.main.fragment.search

import com.zkp.wanandroid.app.App
import com.zkp.wanandroid.base.model.BaseModel
import com.zkp.wanandroid.bean.ArticleResponseBody
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.http.ApiService
import com.zkp.wanandroid.http.AppConfig
import com.zkp.wanandroid.http.HttpsUtil
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