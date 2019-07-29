package com.zkp.wanandroid.db

import com.zkp.wanandroid.db.entity.SearchHistory

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.db
 * @time: 2019/6/4 15:06
 * @description:
 */
interface DbHelper {

    /**
     * 添加一条搜索记录
     * @param data
     */
    fun addSearchHistory(data: String): List<SearchHistory>

    /**
     * 清空所有的搜索纪录
     */
    fun clearAllSearchHistory()

    /**
     * 清空某一条搜索记录
     *
     * @param id
     */
    fun deleteSearchHistoryById(id: Long?)

    /**
     * 加载所有的搜索纪录
     *
     * @return List<HistoryData>
     */
    fun loadAllSearchHistory(): List<SearchHistory>

}