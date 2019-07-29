package com.zkp.wanandroid.http

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.http
 * @time: 2019/5/27 16:06
 * @description:
 */
class NetUtil {

    /**
     * 判断网络是否连接 只能判断网络是否连接，不能判断网络是否可用
     *
     * @param context 上下文
     * @return true--网络已连接  false--网络无连接
     */
    fun isConnected(context: Context): Boolean {

        val manager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val allNetworks = manager.allNetworks
            var networkInfo: NetworkInfo
            for (netWork in allNetworks) {
                networkInfo = manager.getNetworkInfo(netWork)
                if (networkInfo.isConnected) {
                    return true
                }
            }
            return false
        } else {
            val networkIfs = manager.allNetworkInfo
            for (networkInfo in networkIfs) {
                if (networkInfo.isConnected) {
                    return true
                }
            }
            return false
        }

    }

}
