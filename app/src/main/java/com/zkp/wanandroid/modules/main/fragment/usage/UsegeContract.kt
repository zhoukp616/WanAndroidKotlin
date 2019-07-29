package com.zkp.wanandroid.modules.main.fragment.usage

import com.zkp.wanandroid.base.model.IModel
import com.zkp.wanandroid.base.presenter.IPresenter
import com.zkp.wanandroid.base.view.IView
import com.zkp.wanandroid.bean.Friend
import com.zkp.wanandroid.bean.HttpResult
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