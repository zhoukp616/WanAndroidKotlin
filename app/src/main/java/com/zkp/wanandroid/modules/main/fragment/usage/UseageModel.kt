package com.zkp.wanandroid.modules.main.fragment.usage

import com.zkp.wanandroid.app.App
import com.zkp.wanandroid.base.model.BaseModel
import com.zkp.wanandroid.bean.Friend
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.http.ApiService
import com.zkp.wanandroid.http.AppConfig
import com.zkp.wanandroid.http.HttpsUtil
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment.usage
 * @time: 2019/5/31 17:31
 * @description:
 */
class UseageModel : BaseModel(), UsegeContract.Model {
    override fun requestFrienJson(): Observable<HttpResult<MutableList<Friend>>> {
        return HttpsUtil().createApi(App.getContext(), AppConfig().BASE_URL, ApiService::class.java).getFriendJson()
    }
}