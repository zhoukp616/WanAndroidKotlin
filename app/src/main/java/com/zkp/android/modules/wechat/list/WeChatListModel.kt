package com.zkp.android.modules.wechat.list

import com.zkp.android.app.App
import com.zkp.android.base.model.BaseModel
import com.zkp.android.bean.ArticleResponseBody
import com.zkp.android.bean.HttpResult
import com.zkp.android.http.ApiService
import com.zkp.android.http.AppConfig
import com.zkp.android.http.HttpsUtil
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.wechat.list
 * @time: 2019/5/30 10:25
 * @description:
 */
class WeChatListModel : BaseModel(), WeChatListContract.Model {

    override fun requestWeChatArticle(id: Int, page: Int): Observable<HttpResult<ArticleResponseBody>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java)
            .getWeChatArticle(id, page)
    }
}