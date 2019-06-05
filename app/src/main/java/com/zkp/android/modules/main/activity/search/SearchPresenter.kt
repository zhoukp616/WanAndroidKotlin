package com.zkp.android.modules.main.activity.search

import com.zkp.android.base.presenter.BasePresenter
import com.zkp.android.bean.Hotkey
import com.zkp.android.bean.HttpResult
import com.zkp.android.db.DbHelper
import com.zkp.android.db.DbHelperImpl
import com.zkp.android.http.HttpsUtil
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.search
 * @time: 2019/6/4 14:42
 * @description:
 */
@Suppress("UNCHECKED_CAST")
class SearchPresenter : BasePresenter<SearchContract.Model, SearchContract.View>(), SearchContract.Presenter {

    private var dbHelper: DbHelper = DbHelperImpl()

    override fun createModel(): SearchContract.Model? = SearchModel()

    override fun getHotKeys() {
        mView?.showLoading()
        HttpsUtil().request(mModel!!.requestHotKeys(),
            object : HttpsUtil.IResponseListener<HttpResult<MutableList<Hotkey>>> {
                override fun onSuccess(data: HttpResult<MutableList<Hotkey>>) {
                    if (data.errorCode == 0) {
                        mView?.getHotKeysSuccess(data.data)
                    } else {
                        mView?.getHotKeysError(data.errorMsg)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.getHotKeysError(errMsg)
                    mView?.hideLoading()
                }

            })
    }

    override fun addSearchHistory(data: String) {
        doAsync {
            dbHelper.addSearchHistory(data)
        }
    }

    override fun clearAllSearchHistory() {
        doAsync {
            dbHelper.clearAllSearchHistory()
        }
    }

    override fun deleteSearchHistoryById(id: Long?) {
        doAsync {
            dbHelper.deleteSearchHistoryById(id)
        }
    }

    override fun loadAllSearchHistory() {
        doAsync {
            val searchHistoryList = dbHelper.loadAllSearchHistory()
            uiThread {
                mView?.showSearchHistory(searchHistoryList)
            }
        }

    }

}