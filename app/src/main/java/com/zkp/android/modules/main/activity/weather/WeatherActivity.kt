package com.zkp.android.modules.main.activity.weather

import android.util.Log
import android.widget.TextView
import butterknife.BindView
import com.github.matteobattilana.weather.PrecipType
import com.github.matteobattilana.weather.WeatherView
import com.zkp.android.R
import com.zkp.android.base.activity.BaseActivity
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.coder.zzq.smartshow.toast.SmartToast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import com.zkp.android.bean.ForecastResponseBody
import com.zkp.android.bean.RealTimeResponseBody


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

    //声明AMapLocationClient类对象
    private lateinit var mLocationClient: AMapLocationClient
    private lateinit var mCity: String
    //经度
    private var mLongitude: Double = 0.0
    //纬度
    private var mLatitude: Double = 0.0


    override fun createPresenter(): WeatherContract.Presenter = WeatherPresenter()

    override fun initToolbar() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_weather
    }

    override fun initView() {
        mWeatherView.fadeOutPercent = 100f
        mWeatherView.angle = -10
        mWeatherView.speed = 1000
        mWeatherView.emissionRate = 300f
        mWeatherView.setWeatherData(PrecipType.RAIN)

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
                mCity = aMapLocation.city

                mPresenter?.getRealTime(mLongitude, mLatitude)

            } else {
                Log.d("qwe", aMapLocation.errorCode.toString())
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

    override fun getRealTimeSuccess(realTime: RealTimeResponseBody) {

        mTvTemperature.text = realTime.apparent_temperature.toString()
        mTvWether.text = mPresenter?.getWeather(realTime.skycon)
        mTvHumidity.text = realTime.humidity.toString()
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
        mTvColth.text = realTime.comfort.desc

        mPresenter?.getForecast(mLongitude, mLatitude)

    }

    override fun getRealTimeError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun getForecastSuccess(forecast: ForecastResponseBody) {
        Log.d("qwe", forecast.toString())
    }

    override fun getForecastError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

}