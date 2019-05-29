package com.zkp.android.modules.knowledge.list

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
 * @package: com.zkp.android.modules.knowledge.list
 * @time: 2019/5/29 15:47
 * @description:
 */
class KnowledgeListModel : BaseModel(), KnowledgeListContract.Model {

    override fun requestKonwledgeList(page: Int, cid: Int): Observable<HttpResult<ArticleResponseBody>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java)
            .getKnowledgeList(page, cid)
    }
}