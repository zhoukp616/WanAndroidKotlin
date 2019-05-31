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

}