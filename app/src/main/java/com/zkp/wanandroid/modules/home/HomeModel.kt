package com.zkp.wanandroid.modules.home

import com.zkp.wanandroid.app.App
import com.zkp.wanandroid.base.model.BaseModel
import com.zkp.wanandroid.bean.ArticleResponseBody
import com.zkp.wanandroid.bean.Banner
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.http.ApiService
import com.zkp.wanandroid.http.AppConfig
import com.zkp.wanandroid.http.HttpsUtil
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.home
 * @time: 2019/5/28 14:08
 * @description:
 */
class HomeModel : BaseModel(), HomeContract.Model {

    override fun requestBanner(): Observable<HttpResult<MutableList<Banner>>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java).getBanner()
    }

    override fun requestArticleList(page: Int): Observable<HttpResult<ArticleResponseBody>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java)
            .getHomeArticleList(page)
    }

    override fun collectArticle(id: Int): Observable<HttpResult<Any>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java).collectArticle(id)
    }

    override fun unCollectArticle(id: Int): Observable<HttpResult<Any>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java)
            .unCollectArticle(id)
    }
}