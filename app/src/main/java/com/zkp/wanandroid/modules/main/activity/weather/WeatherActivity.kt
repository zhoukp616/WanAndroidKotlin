package com.zkp.wanandroid.modules.main.activity.weather

import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import butterknife.BindView
import com.zkp.wanandroid.R
import com.zkp.wanandroid.base.activity.BaseActivity
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.coder.zzq.smartshow.toast.SmartToast
import com.github.matteobattilana.weather.PrecipType
import com.github.matteobattilana.weather.WeatherView
import com.github.matteobattilana.weather.WeatherViewSensorEventListener
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import com.zkp.wanandroid.bean.ForecastResponseBody
import com.zkp.wanandroid.bean.RealTimeResponseBody
import com.zkp.wanandroid.widget.OnePlusWeatherView
import com.zkp.wanandroid.widget.SunRiseSetView
import com.zkp.wanandroid.widget.WeatherBean
import java.util.*
import com.zkp.wanandroid.widget.SuitLines


/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.weather
 * @time: 2019/6/5 11:37
 * @description:
 */
class WeatherActivity : BaseActivity<WeatherContract.View, WeatherContract.Presenter>(), WeatherContract.View {

    @BindView(R.id.weatherView)
    lateinit var mWeatherView: WeatherView

    @BindView(R.id.refreshLayout)
    lateinit var mRefreshLayout: SmartRefreshLayout

    @BindView(R.id.tvForecastKeypoint)
    lateinit var mTvForecastKeypoint: TextView

    @BindView(R.id.tvDescription)
    lateinit var mTvDescription: TextView

    @BindView(R.id.tvTemperature)
    lateinit var mTvTemperature: TextView

    @BindView(R.id.tvWether)
    lateinit var mTvWether: TextView

    @BindView(R.id.tvHumidity)
    lateinit var mTvHumidity: TextView

    @BindView(R.id.tvAqi)
    lateinit var mTvAqi: TextView

    @BindView(R.id.tvAqiLevel)
    lateinit var mTvAqiLevel: TextView

    @BindView(R.id.tvWindSpeed)
    lateinit var mTvWindSpeed: TextView

    @BindView(R.id.tvWindDirection)
    lateinit var mTvWindDirection: TextView

    @BindView(R.id.tvUltraviolet)
    lateinit var mTvUltraviolet: TextView

    @BindView(R.id.tvVisibility)
    lateinit var mTvVisibility: TextView

    @BindView(R.id.tvIntensity)
    lateinit var mTvIntensity: TextView

    @BindView(R.id.tvPm25)
    lateinit var mTvPm25: TextView

    @BindView(R.id.tvDswrf)
    lateinit var mTvDswrf: TextView

    @BindView(R.id.tvTgwd)
    lateinit var mTvTgwd: TextView

    @BindView(R.id.tvColth)
    lateinit var mTvColth: TextView

    @BindView(R.id.tvCar)
    lateinit var mTvCar: TextView

    @BindView(R.id.tvCold)
    lateinit var mTvCold: TextView

    @BindView(R.id.onePlusWeatherView)
    lateinit var mOnePlusWeatherView: OnePlusWeatherView

    @BindView(R.id.sunRiseSetView)
    lateinit var mSunRiseSetView: SunRiseSetView

    @BindView(R.id.tvSunRise)
    lateinit var mTvSunRise: TextView

    @BindView(R.id.tvSunSet)
    lateinit var mTvSunSet: TextView

    @BindView(R.id.tvDayLong)
    lateinit var mTvDayLong: TextView

    @BindView(R.id.suitLines)
    lateinit var msuitLines: SuitLines

    //声明AMapLocationClient类对象
    private lateinit var mLocationClient: AMapLocationClient
    private lateinit var mCity: String
    //经度
    private var mLongitude: Double = 0.0
    //纬度
    private var mLatitude: Double = 0.0

    lateinit var weatherSensor: WeatherViewSensorEventListener


    override fun createPresenter(): WeatherContract.Presenter = WeatherPresenter()

    override fun initToolbar() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_weather
    }

    override fun initView() {
        weatherSensor = WeatherViewSensorEventListener(this, mWeatherView)

    }

    override fun onResume() {
        super.onResume()
        weatherSensor.start()
    }

    override fun onPause() {
        super.onPause()
        weatherSensor.onPause()
    }

    override fun initEventAndData() {

        initRefreshLayout()

        AndPermission.with(this)
            .runtime()
            .permission(*Permission.Group.LOCATION)
            .onGranted {
                initLocation()
            }
            .onDenied {
                AndPermission.with(this)
                    .runtime()
                    .permission(*Permission.Group.LOCATION)
                    .start()
            }
            .start()

    }

    private fun initLocation() {
        //初始化定位
        mLocationClient = AMapLocationClient(this)
        val mLocationOption = AMapLocationClientOption()
        //高精度模式
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //定位请求超时时间
        mLocationOption.httpTimeOut = 50000
        // 关闭缓存机制
        mLocationOption.isLocationCacheEnable = false
        // 设置是否只定位一次
        mLocationOption.isOnceLocation = true
        //设置参数
        mLocationClient.setLocationOption(mLocationOption)

        //设置定位回调监听
        mLocationClient.setLocationListener { aMapLocation ->
            //定位成功之后取消定位
            mLocationClient.stopLocation()
            if (aMapLocation != null && aMapLocation.errorCode == 0) {
                mLatitude = aMapLocation.latitude
                mLongitude = aMapLocation.longitude

                Log.d("qwe", "mLatitude==$mLatitude")
                Log.d("qwe", "mLongitude==$mLongitude")

                mCity = aMapLocation.city

                mPresenter?.getRealTime(mLongitude, mLatitude)

            } else {
                Log.d("qwe", aMapLocation.errorCode.toString())
                Log.d("qwe", aMapLocation.toString())
                SmartToast.show("定位失败，请重新定位")
            }
        }

        mLocationClient.startLocation()

    }

    private fun initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener { refreshLayout ->
            mPresenter?.getRealTime(mLongitude, mLatitude)
            refreshLayout.finishRefresh()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun getRealTimeSuccess(realTime: RealTimeResponseBody) {

        Log.d("qwe", realTime.toString())

        mTvTemperature.text = realTime.apparent_temperature.toString()
        mTvWether.text = mPresenter?.getWeather(realTime.skycon)
        mTvHumidity.text = realTime.humidity.toString().substring(2) + "%"
        mTvAqi.text = realTime.aqi.toString()
        mTvAqiLevel.text = mPresenter?.getApiLeve(realTime.aqi)
        mTvWindSpeed.text = mPresenter?.getWindSpeed(realTime.wind.speed)
        mTvWindDirection.text = mPresenter?.getWindDirection(realTime.wind.direction)
        mTvUltraviolet.text = realTime.ultraviolet.desc
        mTvVisibility.text = realTime.visibility.toString()
        mTvIntensity.text = realTime.precipitation.local.intensity.toString()
        mTvPm25.text = realTime.pm25.toString()
        mTvDswrf.text = realTime.dswrf.toString()
        mTvTgwd.text = realTime.temperature.toString()

        when {
            realTime.skycon == "RAIN" -> {
                mWeatherView.fadeOutPercent = 100f
                mWeatherView.angle = -10
                mWeatherView.speed = 1000
                mWeatherView.emissionRate = 300f
                mWeatherView.setWeatherData(PrecipType.RAIN)
            }
            realTime.skycon == "SNOW" -> {
                mWeatherView.fadeOutPercent = 100f
                mWeatherView.angle = -10
                mWeatherView.speed = 1000
                mWeatherView.emissionRate = 300f
                mWeatherView.setWeatherData(PrecipType.SNOW)
            }
            else -> {
                mWeatherView.fadeOutPercent = 100f
                mWeatherView.angle = -10
                mWeatherView.speed = 1000
                mWeatherView.emissionRate = 300f
                mWeatherView.setWeatherData(PrecipType.CLEAR)
            }
        }

        mPresenter?.getForecast(mLongitude, mLatitude)

    }

    override fun getRealTimeError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun getForecastSuccess(forecast: ForecastResponseBody) {

        Log.d("qwe", forecast.toString())

        mTvColth.text = forecast.daily.comfort[0].desc
        mTvCar.text = forecast.daily.carWashing[0].desc
        mTvCold.text = forecast.daily.coldRisk[0].desc

        Log.d("qwe", forecast.forecast_keypoint)
        Log.d("qwe", forecast.hourly.description)

        mTvForecastKeypoint.text = forecast.forecast_keypoint
        mTvDescription.text = forecast.hourly.description


        val weatherBeanList = ArrayList<WeatherBean>()
        for (i in 0 until forecast.daily.temperature.size) {
            val weatherBean = WeatherBean(
                mPresenter?.getWeatherId(forecast.daily.skycon_08h_20h[i].value)!!,
                forecast.daily.temperature[i].date.substring(5),
                mPresenter?.getWeek(forecast.daily.temperature[i].date, i),
                forecast.daily.temperature[i].max.toInt(),
                forecast.daily.temperature[i].min.toInt()
            )
            weatherBeanList.add(weatherBean)
        }

        mOnePlusWeatherView.setData(weatherBeanList)

        mSunRiseSetView.setSunrise(
            Integer.parseInt(forecast.daily.astro[0].sunrise.time.substring(0, 2)),
            Integer.parseInt(forecast.daily.astro[0].sunrise.time.substring(3))
        )

        mSunRiseSetView.setSunset(
            Integer.parseInt(forecast.daily.astro[0].sunset.time.substring(0, 2)),
            Integer.parseInt(forecast.daily.astro[0].sunset.time.substring(3))
        )

        mSunRiseSetView.setCurrentTime(
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE)
        )

        mTvSunRise.text = forecast.daily.astro[0].sunrise.time
        mTvSunSet.text = forecast.daily.astro[0].sunset.time

        mTvDayLong.text = mPresenter?.getDayLong(
            forecast.daily.astro[0].sunrise.time,
            forecast.daily.astro[0].sunset.time
        )

        val lines = ArrayList<com.zkp.wanandroid.widget.Unit>()

        for (i in 0 until forecast.hourly.temperature.size) {
            lines.add(
                com.zkp.wanandroid.widget.Unit(
                    forecast.hourly.temperature[i].value.toFloat(),
                    forecast.hourly.temperature[i].datetime.substring(5)
                )
            )
        }
        msuitLines.setLineForm(true)
        msuitLines.setCoverLine(false)
        msuitLines.feedWithAnim(lines)

    }

    override fun getForecastError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

}