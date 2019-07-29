package com.zkp.wanandroid.modules.wechat

import com.zkp.wanandroid.base.model.IModel
import com.zkp.wanandroid.base.presenter.IPresenter
import com.zkp.wanandroid.base.view.IView
import com.zkp.wanandroid.bean.HttpResult
import com.zkp.wanandroid.bean.ProjectTree
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.wechat
 * @time: 2019/5/30 9:26
 * @description: 微信公众号契约类
 */
class WeChatContract {

    interface View : IView {

        /**
         * 获取微信公众号列表成功
         * @param chapters
         */
        fun getWeChatChapterSuccess(chapters: MutableList<ProjectTree>)

        /**
         * 获取微信公众号列表失败
         * @param errorMsg
         */
        fun getWeChatChapterError(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {

        /**
         * 获取微信公众号列表
         */
        fun getWeChatChapters()

    }


    interface Model : IModel {

        /**
         * 请求微信公众号列表
         */
        fun requestWeChatChaptrs(): Observable<HttpResult<MutableList<ProjectTree>>>

    }
}