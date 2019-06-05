package com.zkp.android.modules.main.activity.weather

import com.zkp.android.base.model.IModel
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView
import com.zkp.android.bean.ForecastResponseBody
import com.zkp.android.bean.HttpResultWeather
import com.zkp.android.bean.RealTimeResponseBody
import io.reactivex.Observable

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.weather
 * @time: 2019/6/5 11:35
 * @description:
 */
class WeatherContract {

    interface View : IView {

        /**
         * 获取实时天气成功
         * @param realTime
         */
        fun getRealTimeSuccess(realTime: RealTimeResponseBody)

        /**
         * 获取实时天气失败
         * @param errorMsg
         */
        fun getRealTimeError(errorMsg: String)

        /**
         * 获取预报天气成功
         * @param forecast
         */
        fun getForecastSuccess(forecast: ForecastResponseBody)

        /**
         * 获取预报天气失败
         * @param errorMsg
         */
        fun getForecastError(errorMsg: String)

    }

    interface Presenter : IPresenter<View> {

        /**
         * 获取实时天气
         * @param longitude 经度
         * @param latitude 纬度
         */
        fun getRealTime(longitude: Double, latitude: Double)

        fun getWeather(skyCon: String): String

        fun getApiLeve(aqi: Int): String

        fun getWindSpeed(speed: Double): String

        fun getWindDirection(direction: Double): String

        /**
         * 获取预报天气
         * @param longitude 经度
         * @param latitude 纬度
         */
        fun getForecast(longitude: Double, latitude: Double)

    }

    interface Model : IModel {

        /**
         * 请求获取实时天气
         * @param longitude 经度
         * @param latitude 纬度
         */
        fun requestRealTime(longitude: Double, latitude: Double): Observable<HttpResultWeather<RealTimeResponseBody>>

        /**
         * 请求获取预报天气
         * @param longitude 经度
         * @param latitude 纬度
         */
        fun requestForecast(longitude: Double, latitude: Double): Observable<HttpResultWeather<ForecastResponseBody>>

    }

}