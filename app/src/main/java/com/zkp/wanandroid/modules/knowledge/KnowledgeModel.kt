package com.zkp.wanandroid.modules.knowledge

import com.zkp.wanandroid.app.App
import com.zkp.wanandroid.base.model.BaseModel
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.bean.KnowledgeTreeBody
import com.zkp.wanandroid.http.ApiService
import com.zkp.wanandroid.http.AppConfig
import com.zkp.wanandroid.http.HttpsUtil
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.knowledge
 * @time: 2019/5/29 13:56
 * @description:
 */
class KnowledgeModel : BaseModel(), KnowledgeContract.Model {

    override fun requestKnowledgeTree(): Observable<HttpResult<MutableList<KnowledgeTreeBody>>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java).getKnowledgeTree()
    }
}