package com.zkp.android.modules.knowledge

import com.zkp.android.app.App
import com.zkp.android.base.model.BaseModel
import com.zkp.android.bean.HttpResult
import com.zkp.android.bean.KnowledgeTreeBody
import com.zkp.android.http.ApiService
import com.zkp.android.http.AppConfig
import com.zkp.android.http.HttpsUtil
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.knowledge
 * @time: 2019/5/29 13:56
 * @description:
 */
class KnowledgeModel : BaseModel(), KnowledgeContract.Model {

    override fun requestKnowledgeTree(): Observable<HttpResult<List<KnowledgeTreeBody>>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java).getKnowledgeTree()
    }
}