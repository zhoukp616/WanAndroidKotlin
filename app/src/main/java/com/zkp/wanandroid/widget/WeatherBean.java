package com.zkp.wanandroid.widget;

/**
 * @author: zkp
 * @project: Gank
 * @package: com.zkp.gank.widget
 * @time: 2019/5/14 15:30
 * @description:
 */
public class WeatherBean {

    /**
     * 晴
     */
    public static final int SUN = 1;
    /**
     * 阴
     */
    public static final int CLOUDY = 2;
    /**
     * 雪
     */
    public static final int SNOW = 3;
    /**
     * 雨
     */
    public static final int RAIN = 4;
    /**
     * 多云
     */
    public static final int SUN_CLOUDY = 5;
    /**
     * 打雷
     */
    public static final int THUNDER = 6;

    /**
     * 天气标识，取值为上面6种
     */
    private int weatherId;
    /**
     * 日期
     */
    private String date;
    /**
     * 周，星期
     */
    private String week;
    /**
     * 最高温度
     */
    private int maxTemperature;
    /**
     * 最低温度
     */
    private int minTemperature;

    public WeatherBean(int weatherId, String date, String week, int maxTemperature, int minTemperature) {
        this.weatherId = weatherId;
        this.date = date;
        this.week = week;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }

    public static int[] getAllWeatherId() {
        return new int[]{SUN, CLOUDY, SNOW, RAIN, SUN_CLOUDY, THUNDER};
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        this.minTemperature = minTemperature;
    }

}
