package com.zkp.android.modules.wechat

import com.zkp.android.app.App
import com.zkp.android.base.model.BaseModel
import com.zkp.android.bean.HttpResult
import com.zkp.android.bean.ProjectTree
import com.zkp.android.http.ApiService
import com.zkp.android.http.AppConfig
import com.zkp.android.http.HttpsUtil
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.wechat
 * @time: 2019/5/30 9:27
 * @description:
 */
class WeChatModel : BaseModel(), WeChatContract.Model {

    override fun requestWeChatChaptrs(): Observable<HttpResult<MutableList<ProjectTree>>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java).getWeChatChapters()
    }

}