package com.zkp.android.http

import android.os.Environment
import java.io.File

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.http
 * @time: 2019/5/27 16:10
 * @description:
 */
class AppConfig {

    val APP_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "玩安卓Kotlin"

    /**
     * 读取超时 默认设置为10s
     */
    val TIMEOUT_READ = 10L
    /**
     * 连接超时 默认设置为10s
     */
    val TIMEOUT_CONNECTION = 10L

    val BASE_URL = "https://www.wanandroid.com/"
    val BASE_URL_GANK = "http://gank.io/"

    val CURRENT_FRAGMENT_INDEX = "current_fragment"

    val SHARED_PREFERENCES_NAME = "WanAndroidZkpAPP"

    /**
     * 首页
     */
    val TYPE_HOME_PAGER = 0
    /**
     * 知识体系
     */
    val TYPE_KNOWLEDGE = 1
    /**
     * 微信公众号
     */
    val TYPE_WX_ARTICLE = 2
    /**
     * 导航
     */
    val TYPE_NAVIGATION = 3
    /**
     * 项目
     */
    val TYPE_PROJECT = 4

    /**
     * 我的收藏
     */
    val TYPE_COLLECT = 5
    /**
     * 福利
     */
    val TYPE_WELFARE = 6
    /**
     * 天气
     */
    val TYPE_WEATHER = 7
    /**
     * 设置
     */
    val TYPE_SETTING = 8
    /**
     * 关于
     */
    val TYPE_ABOUT_US = 9
    /**
     * 常用网站
     */
    val TYPE_USEFUL_SITES = 10
    /**
     * 搜索结果
     */
    val TYPE_SEARCH_RESULT = 11
    /**
     * 测试博客
     */
    val TYPE_CNBLOG = 12
    val CNBLOGS_URL = "https://www.cnblogs.com/imyalost/category/873684.html"
    val CNBLOGS_AUTHOR = "老_张"

    /**
     * TODO分类
     */
    val TODO_TYPE_ALL = 0
    val TODO_TYPE_WORK = 1
    val TODO_TYPE_STUDY = 2
    val TODO_TYPE_LIFE = 3
    val TODO_TYPE_OTHER = 4

    /**
     * TODO列表参数
     */
    val KEY_TODO_TITLE = "title"
    val KEY_TODO_CONTENT = "content"
    val KEY_TODO_DATE = "date"
    val KEY_TODO_TYPE = "type"
    val KEY_TODO_STATUS = "status"
    val KEY_TODO_PRIORITY = "priority"
    val KEY_TODO_ORDERBY = "orderby"

    val TODO_PRIORITY_FIRST = 1
    val TODO_PRIORITY_SECOND = 2

    val URL_UPDATE = "http://mock-api.com/3EgdX1gM.mock/getUpdateInfo"
    /**
     * About Url
     */
    val ABOUT_WEBSITE = "https://www.wanandroid.com/about"
    val ABOUT_SOURCE_CODE = "https://github.com/Zkp275557625/WanAndroidKotlin"
    val ABOUT_FEEDBACK = "https://github.com/Zkp275557625/WanAndroidKotlin/issues"

}