package com.zkp.android.utils

import android.content.Context
import com.zkp.android.http.AppConfig

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.utils
 * @time: 2019/5/31 9:58
 * @description:
 */
class SpUtils {

    /**
     * 得到缓存的boolean值
     *
     * @param context 上下文
     * @param key     key
     * @return boolean
     */
    fun getBoolean(context: Context, key: String): Boolean {
        val sp = context.getSharedPreferences(AppConfig().SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(key, false)
    }

    /**
     * @param context 上下文
     * @param key     key
     * @param value   vlaue
     */
    fun putBoolean(context: Context, key: String, value: Boolean) {
        val sp = context.getSharedPreferences(AppConfig().SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        sp.edit().putBoolean(key, value).apply()
    }

    /**
     * 缓存文本数据
     *
     * @param context 上下文
     * @param key     key
     * @param value   value
     */
    fun putString(context: Context, key: String, value: String) {
        val sp = context.getSharedPreferences(AppConfig().SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        sp.edit().putString(key, value).apply()
    }

    /**
     * 获取缓存的字符串信息
     *
     * @param context 上下文
     * @param key     key
     * @return String
     */
    fun getString(context: Context, key: String): String? {
        val sp = context.getSharedPreferences(AppConfig().SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sp.getString(key, "")
    }

}