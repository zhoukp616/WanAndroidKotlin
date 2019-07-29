package com.zkp.wanandroid.modules.main.activity.weather

import com.zkp.wanandroid.app.App
import com.zkp.wanandroid.base.model.BaseModel
import com.zkp.wanandroid.bean.ForecastResponseBody
import com.zkp.wanandroid.bean.HttpResultWeather
import com.zkp.wanandroid.bean.RealTimeResponseBody
import com.zkp.wanandroid.http.ApiService
import com.zkp.wanandroid.http.AppConfig
import com.zkp.wanandroid.http.HttpsUtilWeather
import io.reactivex.Observable

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.weather
 * @time: 2019/6/5 11:36
 * @description:
 */
class WeatherModel : BaseModel(), WeatherContract.Model {
    override fun requestRealTime(
        longitude: Double,
        latitude: Double
    ): Observable<HttpResultWeather<RealTimeResponseBody>> {
        return HttpsUtilWeather().createApi(App.getContext(), AppConfig().BASE_URL_WEATHER, ApiService::class.java)
            .getRealTime(longitude, latitude)
    }

    override fun requestForecast(
        longitude: Double,
        latitude: Double
    ): Observable<HttpResultWeather<ForecastResponseBody>> {
        return HttpsUtilWeather().createApi(App.getContext(), AppConfig().BASE_URL_WEATHER, ApiService::class.java)
            .getForecast(longitude, latitude)
    }
}