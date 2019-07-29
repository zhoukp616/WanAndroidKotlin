package com.zkp.wanandroid.modules.navigation

import com.zkp.wanandroid.base.model.IModel
import com.zkp.wanandroid.base.presenter.IPresenter
import com.zkp.wanandroid.base.view.IView
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.bean.Navigation
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.navigation
 * @time: 2019/5/30 11:34
 * @description:
 */
class NavigationContract {

    interface View : IView {

        /**
         * 获取导航数据成功
         * @param navigationList
         * @param isFresh 是否刷新
         */
        fun getNaviJsonSuccess(navigationList: MutableList<Navigation>)

        /**
         * 获取导航数据失败
         * @param errorMsg
         */
        fun getNaviJsonError(errorMsg: String)

    }

    interface Presenter : IPresenter<View> {

        /**
         * 获取导航数据
         * @param isFresh 是否刷新
         */
        fun getNaviJson()

        /**
         * 刷新数据
         */
        fun refresh()

    }

    interface Model : IModel {

        /**
         * 请求导航数据
         */
        fun requestNaviJson(): Observable<HttpResult<MutableList<Navigation>>>

    }

}