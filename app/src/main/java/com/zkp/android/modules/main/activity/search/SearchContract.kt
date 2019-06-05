package com.zkp.android.modules.main.activity.search

import com.zkp.android.base.model.IModel
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView
import com.zkp.android.bean.Hotkey
import com.zkp.android.bean.HttpResult
import com.zkp.android.db.entity.SearchHistory
import io.reactivex.Observable

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.search
 * @time: 2019/6/4 14:40
 * @description:
 */
class SearchContract {

    interface View : IView {

        /**
         * 获取搜索热词成功
         * @param hotkeys
         */
        fun getHotKeysSuccess(hotkeys: MutableList<Hotkey>)

        /**
         * 获取搜索热词失败
         * @param errorMsg
         */
        fun getHotKeysError(errorMsg: String)

        /**
         * 显示搜索纪录
         *
         * @param historyDataList
         */
        fun showSearchHistory(historyDataList: List<SearchHistory>)

    }

    interface Presenter : IPresenter<View> {

        /**
         * 获取搜索热词
         */
        fun getHotKeys()

        /**
         * 新增一条搜索纪录
         *
         * @param data String
         */
        fun addSearchHistory(data: String)

        /**
         * 清除所有的搜索纪录
         */
        fun clearAllSearchHistory()

        /**
         * 清除某一条搜索记录
         *
         * @param id
         */
        fun deleteSearchHistoryById(id: Long?)

        /**
         * 加载所有的搜索记录
         */
        fun loadAllSearchHistory()

    }

    interface Model : IModel {

        /**
         * 请求获取搜索热词
         */
        fun requestHotKeys(): Observable<HttpResult<MutableList<Hotkey>>>

    }

}