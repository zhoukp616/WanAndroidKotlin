package com.zkp.android.http

import android.util.Log
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.zkp.android.app.App
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.http
 * @time: 2019/5/27 15:59
 * @description: http请求工具类
 */
class HttpUtil {

    private lateinit var mOkHttpClient: OkHttpClient

    /**
     * 创建网络接口实例
     */
    fun <T> createApi(baseUrl: String, service: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getInstance())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(service)
    }

    private fun getInstance(): OkHttpClient {
        mOkHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .readTimeout(AppConfig().TIMEOUT_READ, TimeUnit.SECONDS)
            .connectTimeout(AppConfig().TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .hostnameVerifier { _, _ -> true }
            .cookieJar(
                PersistentCookieJar(
                    SetCookieCache(),
                    SharedPrefsCookiePersistor(App.getContext())
                )
            )
            .build()
        return mOkHttpClient
    }

    fun <T> request(observable: Observable<T>, listener: IResponseListener<T>?) {
        if (!NetUtil().isConnected(App.getContext())) {
            listener?.onFail("网络无连接")
            return
        }
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<T> {

                override fun onSubscribe(disposable: Disposable) {

                }

                override fun onNext(data: T) {
                    listener?.onSuccess(data)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    Log.d("qwe", e.message)
                    listener?.onFail("连接失败")
                }

                override fun onComplete() {

                }
            }
            )
    }

    interface IResponseListener<T> {

        /**
         * 请求成功回调
         *
         * @param data JavaBean
         */
        fun onSuccess(data: T)

        /**
         * 请求失败回调
         *
         * @param errMsg String 错误信息
         */
        fun onFail(errMsg: String)
    }

}