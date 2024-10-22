package com.zkp.wanandroid.modules.main.activity.weather

import com.zkp.wanandroid.base.model.IModel
import com.zkp.wanandroid.base.presenter.IPresenter
import com.zkp.wanandroid.base.view.IView
import com.zkp.wanandroid.bean.ForecastResponseBody
import com.zkp.wanandroid.bean.HttpResultWeather
import com.zkp.wanandroid.bean.RealTimeResponseBody
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

        /**
         * 获取天气id
         *
         * @param weather
         * @return
         */
        fun getWeatherId(weather: String): Int

        /**
         * 获取星期几
         *
         * @param date  日期
         * @param index 索引 index=0返回今日
         * @return
         */
        fun getWeek(date: String, index: Int): String

        /**
         * 计算昼长
         *
         * @param sunRise 日出时间 05：12
         * @param sunSet  日落时间 18:56
         * @return
         */
        fun getDayLong(sunRise: String, sunSet: String): String

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