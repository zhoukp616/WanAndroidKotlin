package com.zkp.android.http

import android.content.Context
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
import java.io.IOException
import java.io.InputStream
import java.security.GeneralSecurityException
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.http
 * @time: 2019/5/27 15:59
 * @description: https请求工具类
 */
class HttpsUtil {

    private lateinit var mOkHttpClient: OkHttpClient

    /**
     * 创建网络接口实例
     */
    fun <T> createApi(context: Context, baseUrl: String, service: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getInstance(context))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(service)
    }

    private fun getInstance(context: Context): OkHttpClient {

        val trustManager: X509TrustManager
        val sslSocketFactory: SSLSocketFactory
        val inputStream: InputStream
        try {
            //得到证书的输入流
            inputStream = context.assets.open("wanandroid.crt")

            //以流的方式读入证书
            trustManager = trustManagerForCertificates(inputStream)
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
            sslSocketFactory = sslContext.socketFactory

            mOkHttpClient = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(AppConfig().TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(AppConfig().TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .hostnameVerifier { _, _ -> true }
                .sslSocketFactory(sslSocketFactory, trustManager)
                .cookieJar(
                    PersistentCookieJar(
                        SetCookieCache(),
                        SharedPrefsCookiePersistor(App.getContext())
                    )
                )
                .build()

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
        }

        return mOkHttpClient

    }

    @Throws(GeneralSecurityException::class)
    private fun trustManagerForCertificates(`in`: InputStream): X509TrustManager {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val certificates = certificateFactory.generateCertificates(`in`)
        if (certificates.isEmpty()) {
            throw IllegalArgumentException("expected non-empty set of trusted certificates")
        }

        // Put the certificates a key store. Any password will work.
        val password = "password".toCharArray()
        val keyStore = newEmptyKeyStore(password)

        for ((index, certificate) in certificates.withIndex()) {
            val certificateAlias = Integer.toString(index)
            keyStore.setCertificateEntry(certificateAlias, certificate)
        }

        // Use it to build an X509 trust manager.
        val keyManagerFactory = KeyManagerFactory.getInstance(
            KeyManagerFactory.getDefaultAlgorithm()
        )
        keyManagerFactory.init(keyStore, password)
        val trustManagerFactory = TrustManagerFactory.getInstance(
            TrustManagerFactory.getDefaultAlgorithm()
        )
        trustManagerFactory.init(keyStore)
        val trustManagers = trustManagerFactory.trustManagers
        if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
            throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
        }
        return trustManagers[0] as X509TrustManager
    }

    /**
     * 添加password
     *
     * @param password 密码
     * @return KeyStore
     * @throws GeneralSecurityException
     */
    @Throws(GeneralSecurityException::class)
    private fun newEmptyKeyStore(password: CharArray): KeyStore {
        try {
            // 这里添加自定义的密码，默认
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            // By convention, 'null' creates an empty key store.
            val `in`: InputStream? = null
            keyStore.load(`in`, password)
            return keyStore
        } catch (e: IOException) {
            throw AssertionError(e)
        }

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