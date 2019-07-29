package com.zkp.wanandroid.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.utils
 * @time: 2019/5/27 15:48
 * @description:
 */
class CommonUtils {

    /**
     * 隐藏键盘
     */
    fun hideKeyBoard(context: Context, view: View) {
        val inputMethodManager =
            context.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isActive) {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

}