package com.zkp.android.http

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.http
 * @time: 2019/5/27 16:10
 * @description:
 */
class AppConfig {

    /**
     * 读取超时 默认设置为10s
     */
    val TIMEOUT_READ = 10L
    /**
     * 连接超时 默认设置为10s
     */
    val TIMEOUT_CONNECTION = 10L

    val BASE_URL = "https://www.wanandroid.com/"

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

}