package com.zkp.android.modules.main.Fragment.usage

import com.zkp.android.app.App
import com.zkp.android.base.model.BaseModel
import com.zkp.android.bean.Friend
import com.zkp.android.bean.HttpResult
import com.zkp.android.http.ApiService
import com.zkp.android.http.AppConfig
import com.zkp.android.http.HttpsUtil
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