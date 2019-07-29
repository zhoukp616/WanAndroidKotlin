package com.zkp.wanandroid.db

import android.util.Log
import com.zkp.wanandroid.app.App
import com.zkp.wanandroid.db.entity.SearchHistory
import com.zkp.wanandroid.db.greendao.DaoSession
import com.zkp.wanandroid.http.AppConfig

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.db
 * @time: 2019/6/4 15:10
 * @description:
 */
class DbHelperImpl : DbHelper {

    private lateinit var searchString: String
    private lateinit var searchHistoryList: MutableList<SearchHistory>
    private lateinit var searchHistory: SearchHistory

    companion object {

        private var daoSession: DaoSession = App.getDaoSession()

    }

    override fun addSearchHistory(data: String): List<SearchHistory> {
        this.searchString = data
        getSearchHistoryList()
        createSearchHistory()

        if (searchHistoryForward()) {
            return searchHistoryList
        }

        if (searchHistoryList.size < AppConfig().HISTORY_LIST_SIZE) {
            daoSession.searchHistoryDao.insert(searchHistory)
        } else {
            searchHistoryList.removeAt(0)
            searchHistoryList.add(searchHistory)
            daoSession.searchHistoryDao.deleteAll()
            daoSession.searchHistoryDao.insertInTx(searchHistoryList)
        }
        return searchHistoryList
    }

    private fun getSearchHistoryList() {
        searchHistoryList = daoSession.searchHistoryDao.loadAll()
    }

    private fun createSearchHistory() {
        searchHistory = SearchHistory()
        searchHistory.date = System.currentTimeMillis()
        searchHistory.data = searchString
        Log.d("qwe", searchString)
    }

    /**
     * 历史数据前移
     *
     * @return 返回true表示查询的数据已存在，只需将其前移到第一项历史记录，否则需要增加新的历史记录
     */
    private fun searchHistoryForward(): Boolean {
        //重复搜索时进行历史记录前移
        val iterator = searchHistoryList.iterator()
        //不要在foreach循环中进行元素的remove、add操作，使用Iterator模式
        while (iterator.hasNext()) {
            val searchHistory1 = iterator.next()
            if (searchHistory1.data == searchString) {
                searchHistoryList.remove(searchHistory1)
                searchHistoryList.add(searchHistory)
                daoSession.searchHistoryDao.deleteAll()
                daoSession.searchHistoryDao.insertInTx(searchHistoryList)
                return true
            }
        }
        return false
    }

    override fun clearAllSearchHistory() {
        daoSession.searchHistoryDao.deleteAll()
    }

    override fun deleteSearchHistoryById(id: Long?) {
        daoSession.searchHistoryDao.deleteByKey(id)
    }

    override fun loadAllSearchHistory(): List<SearchHistory> {
        return daoSession.searchHistoryDao.loadAll()
    }
}