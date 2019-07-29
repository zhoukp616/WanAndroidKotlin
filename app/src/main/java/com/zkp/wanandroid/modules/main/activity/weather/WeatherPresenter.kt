package com.zkp.wanandroid.modules.main.activity.weather

import android.annotation.SuppressLint
import com.zkp.wanandroid.base.presenter.BasePresenter
import com.zkp.wanandroid.bean.ForecastResponseBody
import com.zkp.wanandroid.bean.HttpResultWeather
import com.zkp.wanandroid.bean.RealTimeResponseBody
import com.zkp.wanandroid.http.HttpsUtilWeather
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.weather
 * @time: 2019/6/5 11:36
 * @description:
 */
class WeatherPresenter : BasePresenter<WeatherContract.Model, WeatherContract.View>(), WeatherContract.Presenter {

    override fun createModel(): WeatherContract.Model? = WeatherModel()

    override fun getRealTime(longitude: Double, latitude: Double) {
        mView?.showLoading()
        HttpsUtilWeather().request(mModel!!.requestRealTime(longitude, latitude),
            object : HttpsUtilWeather.IResponseListener<HttpResultWeather<RealTimeResponseBody>> {
                override fun onSuccess(data: HttpResultWeather<RealTimeResponseBody>) {
                    if (data.status == "ok") {
                        mView?.getRealTimeSuccess(data.result)
                    } else {
                        mView?.getRealTimeError(data.status)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.getRealTimeError(errMsg)
                    mView?.hideLoading()
                }

            })
    }

    override fun getForecast(longitude: Double, latitude: Double) {
        mView?.showLoading()
        HttpsUtilWeather().request(mModel!!.requestForecast(longitude, latitude),
            object : HttpsUtilWeather.IResponseListener<HttpResultWeather<ForecastResponseBody>> {
                override fun onSuccess(data: HttpResultWeather<ForecastResponseBody>) {
                    if (data.status == "ok") {
                        mView?.getForecastSuccess(data.result)
                    } else {
                        mView?.getForecastError(data.status)
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.getForecastError(errMsg)
                    mView?.hideLoading()
                }

            })
    }

    override fun getWeather(skyCon: String): String {
        when (skyCon) {
            "CLEAR_DAY", "CLEAR_NIGHT" -> return "晴"
            "PARTLY_CLOUDY_DAY", "PARTLY_CLOUDY_NIGHT" -> return "多云"
            "CLOUDY" -> return "阴"
            "WIND" -> return "大风"
            "HAZE" -> return "雾霾"
            "RAIN" -> return "雨"
            "SNOW" -> return "雪"
        }
        return "晴"
    }

    override fun getApiLeve(aqi: Int): String {
        return when {
            aqi <= 50 -> "优"
            aqi <= 100 -> "良"
            aqi <= 150 -> "轻度污染"
            aqi <= 200 -> "中度污染"
            aqi <= 300 -> "重度污染"
            aqi <= 400 -> "严重污染"
            else -> "救救孩子吧"
        }
    }

    override fun getWindSpeed(speed: Double): String {
        return when {
            speed < 1 -> "0级"
            speed <= 5 -> "1级"
            speed <= 11 -> "2级"
            speed <= 19 -> "3级"
            speed <= 28 -> "4级"
            speed <= 38 -> "5级"
            speed <= 49 -> "6级"
            speed <= 61 -> "7级"
            speed <= 74 -> "8级"
            speed <= 88 -> "9级"
            speed <= 102 -> "10级"
            speed <= 117 -> "11级"
            speed <= 134 -> "12级"
            speed <= 149 -> "13级"
            speed <= 166 -> "14级"
            speed <= 183 -> "15级"
            speed <= 201 -> "16级"
            speed <= 220 -> "17级"
            else -> ">17级"
        }
    }

    override fun getWindDirection(direction: Double): String {
        return when {
            direction == 0.0 -> "北风"
            direction < 90 -> "东北"
            direction == 90.0 -> "东风"
            direction < 180 -> "东南"
            direction == 180.0 -> "南风"
            direction < 270 -> "西南"
            direction == 270.0 -> "西风"
            direction < 360 -> "西北"
            else -> "北风"
        }
    }

    override fun getWeatherId(weather: String): Int {
        when (weather) {
            "CLEAR_DAY", "CLEAR_NIGHT" -> return 1
            "CLOUDY" -> return 2
            "SNOW" -> return 3
            "RAIN" -> return 4
            "PARTLY_CLOUDY_DAY", "PARTLY_CLOUDY_NIGHT" -> return 5
            "WIND" -> return 6
            "HAZE" -> return 6
        }
        return 1
    }

    override fun getWeek(dateString: String, index: Int): String {
        val week: String
        val weekDays = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
        if (index == 0) {
            week = "今日"
        } else {
            @SuppressLint("SimpleDateFormat")
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val cal = Calendar.getInstance()
            val date: Date
            try {
                date = sdf.parse(dateString)
                cal.time = date
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            week = weekDays[cal.get(Calendar.DAY_OF_WEEK) - 1]
        }
        return week
    }

    override fun getDayLong(sunRise: String, sunSet: String): String {
        val dayLong = StringBuilder()

        when {
            Integer.parseInt(sunSet.substring(3)) < Integer.parseInt(sunRise.substring(3)) -> dayLong.append(
                Integer.parseInt(
                    sunSet.substring(0, 2)
                ) - 1 - Integer.parseInt(sunRise.substring(0, 2))
            )
                .append("时")
                .append(60 + Integer.parseInt(sunSet.substring(3)) - Integer.parseInt(sunRise.substring(3)))
                .append("分")
            Integer.parseInt(sunSet.substring(3)) > Integer.parseInt(sunRise.substring(3)) -> dayLong.append(
                Integer.parseInt(
                    sunSet.substring(0, 2)
                ) - Integer.parseInt(sunRise.substring(0, 2))
            )
                .append("时")
                .append(Integer.parseInt(sunSet.substring(3)) - Integer.parseInt(sunRise.substring(3)))
                .append("分")
            else -> dayLong.append(Integer.parseInt(sunSet.substring(0, 2)) - Integer.parseInt(sunRise.substring(0, 2)))
                .append("时")
        }

        return dayLong.toString()
    }

}