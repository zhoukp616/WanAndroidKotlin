package com.zkp.android.modules.main.fragment.usage

import com.zkp.android.base.model.IModel
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView
import com.zkp.android.bean.Friend
import com.zkp.android.bean.HttpResult
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment.usage
 * @time: 2019/5/31 17:29
 * @description:
 */
class UsegeContract {

    interface View : IView {

        /**
         * 获取常用网站数据成功
         */
        fun getFriendJsonSuccess(friendList: MutableList<Friend>)

        /**
         * 获取常用网站数据失败
         * @param errorMsg
         */
        fun getFriendJsonError(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {

        /**
         * 获取常用网站数据
         */
        fun getFriendJson()
    }

    interface Model : IModel {

        /**
         * 请求常用网站数据
         */
        fun requestFrienJson(): Observable<HttpResult<MutableList<Friend>>>

    }


}