package com.zkp.android.modules.main.activity.weather

import com.zkp.android.app.App
import com.zkp.android.base.model.BaseModel
import com.zkp.android.bean.ForecastResponseBody
import com.zkp.android.bean.HttpResultWeather
import com.zkp.android.bean.RealTimeResponseBody
import com.zkp.android.http.ApiService
import com.zkp.android.http.AppConfig
import com.zkp.android.http.HttpsUtilWeather
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