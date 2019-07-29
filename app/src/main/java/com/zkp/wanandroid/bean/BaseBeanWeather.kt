package com.zkp.wanandroid.bean

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.bean
 * @time: 2019/5/28 14:36
 * @description:
 */
open class BaseBeanWeather {
    var api_status: String = ""
    var api_version: String = ""
    var lang: String = ""
    var server_time: Long = 0
    var status: String = ""
    var tzshift: Long = 0
    var unit: String = ""
}