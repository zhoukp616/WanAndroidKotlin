package com.zkp.wanandroid.modules.wechat

import com.zkp.wanandroid.app.App
import com.zkp.wanandroid.base.model.BaseModel
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.bean.ProjectTree
import com.zkp.wanandroid.http.ApiService
import com.zkp.wanandroid.http.AppConfig
import com.zkp.wanandroid.http.HttpsUtil
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