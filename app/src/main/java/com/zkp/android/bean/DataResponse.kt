package com.zkp.android.bean

import com.squareup.moshi.Json
import java.io.Serializable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.bean
 * @time: 2019/5/28 14:40
 * @description:
 */
data class HttpResult<T>(
    @Json(name = "data") val data: T
) : BaseBean()

//首页文章列表
data class ArticleResponseBody(
    @Json(name = "curPage") val curPage: Int,
    @Json(name = "datas") var datas: MutableList<Article>,
    @Json(name = "offset") val offset: Int,
    @Json(name = "over") val over: Boolean,
    @Json(name = "pageCount") val pageCount: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "total") val total: Int
)

data class Article(
    @Json(name = "apkLink") val apkLink: String,
    @Json(name = "author") val author: String,
    @Json(name = "chapterId") val chapterId: Int,
    @Json(name = "chapterName") val chapterName: String,
    @Json(name = "collect") val collect: Boolean,
    @Json(name = "courseId") val courseId: Int,
    @Json(name = "desc") val desc: String,
    @Json(name = "envelopePic") val envelopePic: String,
    @Json(name = "fresh") val fresh: Boolean,
    @Json(name = "id") val id: Int,
    @Json(name = "link") val link: String,
    @Json(name = "niceDate") val niceDate: String,
    @Json(name = "origin") val origin: String,
    @Json(name = "prefix") val prefix: String,
    @Json(name = "projectLink") val projectLink: String,
    @Json(name = "publishTime") val publishTime: Long,
    @Json(name = "superChapterId") val superChapterId: Int,
    @Json(name = "superChapterName") val superChapterName: String,
    @Json(name = "tags") val tags: MutableList<Tag>,
    @Json(name = "title") val title: String,
    @Json(name = "type") val type: Int,
    @Json(name = "userId") val userId: Int,
    @Json(name = "visible") val visible: Int,
    @Json(name = "zan") val zan: Int
)

data class Tag(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)

//首页轮播图
data class Banner(
    @Json(name = "desc") val desc: String,
    @Json(name = "id") val id: Int,
    @Json(name = "imagePath") val imagePath: String,
    @Json(name = "isVisible") val isVisible: Int,
    @Json(name = "order") val order: Int,
    @Json(name = "title") val title: String,
    @Json(name = "type") val type: Int,
    @Json(name = "url") val url: String
)

//知识体系
data class KnowledgeTreeBody(
    @Json(name = "children") val children: MutableList<Knowledge>,
    @Json(name = "courseId") val courseId: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "order") val order: Int,
    @Json(name = "parentChapterId") val parentChapterId: Int,
    @Json(name = "userControlSetTop") val userControlSetTop: Boolean,
    @Json(name = "visible") val visible: Int
) : Serializable

data class Knowledge(
    @Json(name = "children") val children: List<Any>,
    @Json(name = "courseId") val courseId: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "order") val order: Int,
    @Json(name = "parentChapterId") val parentChapterId: Int,
    @Json(name = "userControlSetTop") val userControlSetTop: Boolean,
    @Json(name = "visible") val visible: Int
) : Serializable

//导航数据
data class Navigation(
    @Json(name = "articles") val articles: MutableList<Article>,
    @Json(name = "cid") val cid: Int,
    @Json(name = "name") val name: String
)

//项目分类 微信列表
data class ProjectTree(
    @Json(name = "children") val children: List<Any>,
    @Json(name = "courseId") val courseId: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "order") val order: Int,
    @Json(name = "parentChapterId") val parentChapterId: Int,
    @Json(name = "userControlSetTop") val userControlSetTop: Boolean,
    @Json(name = "visible") val visible: Int
)

// 登录数据
data class Login(
    @Json(name = "chapterTops") val chapterTops: MutableList<String>,
    @Json(name = "collectIds") val collectIds: MutableList<String>,
    @Json(name = "email") val email: String,
    @Json(name = "icon") val icon: String,
    @Json(name = "id") val id: Int,
    @Json(name = "password") val password: String,
    @Json(name = "token") val token: String,
    @Json(name = "type") val type: Int,
    @Json(name = "username") val username: String
)

data class CollectResponseBody<T>(
    @Json(name = "curPage") val curPage: Int,
    @Json(name = "datas") val datas: MutableList<T>,
    @Json(name = "offset") val offset: Int,
    @Json(name = "over") val over: Boolean,
    @Json(name = "pageCount") val pageCount: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "total") val total: Int
)

data class CollectArticle(
    @Json(name = "author") val author: String,
    @Json(name = "chapterId") val chapterId: Int,
    @Json(name = "chapterName") val chapterName: String,
    @Json(name = "courseId") val courseId: Int,
    @Json(name = "desc") val desc: String,
    @Json(name = "envelopePic") val envelopePic: String,
    @Json(name = "id") val id: Int,
    @Json(name = "link") val link: String,
    @Json(name = "niceDate") val niceDate: String,
    @Json(name = "origin") val origin: String,
    @Json(name = "originId") val originId: Int,
    @Json(name = "publishTime") val publishTime: Long,
    @Json(name = "title") val title: String,
    @Json(name = "userId") val userId: Int,
    @Json(name = "visible") val visible: Int,
    @Json(name = "zan") val zan: Int
)

//常用网站
data class Friend(
    @Json(name = "icon") val icon: String,
    @Json(name = "id") val id: Int,
    @Json(name = "link") val link: String,
    @Json(name = "name") val name: String,
    @Json(name = "order") val order: Int,
    @Json(name = "visible") val visible: Int
)

//搜索热词
data class Hotkey(
    @Json(name = "id") val id: Int,
    @Json(name = "link") val link: String,
    @Json(name = "name") val name: String,
    @Json(name = "order") val order: Int,
    @Json(name = "visible") val visible: Int
)

data class ToDoResponseBody(
    @Json(name = "curPage") val curPage: Int,
    @Json(name = "datas") val datas: MutableList<ToDo>,
    @Json(name = "offset") val offset: Int,
    @Json(name = "over") val over: Boolean,
    @Json(name = "pageCount") val pageCount: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "total") val total: Int
)

data class ToDo(
    @Json(name = "completeDateStr") val completeDateStr: String,
    @Json(name = "content") val content: String,
    @Json(name = "date") val date: Long,
    @Json(name = "dateStr") val dateStr: String,
    @Json(name = "id") val id: Int,
    @Json(name = "priority") val priority: Int,
    @Json(name = "status") val status: Int,
    @Json(name = "title") val title: String,
    @Json(name = "type") val type: Int,
    @Json(name = "userId") val userId: Int
) : Serializable

data class HttpResultGank<T>(
    @Json(name = "results") val results: T
) : BaseBeanGank()

data class WelFare(
    @Json(name = "_id") val _id: String,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "desc") val desc: String,
    @Json(name = "publishedAt") val publishedAt: String,
    @Json(name = "source") val source: String,
    @Json(name = "type") val type: String,
    @Json(name = "url") val url: String,
    @Json(name = "used") val used: Boolean,
    @Json(name = "who") val who: String
)

data class HttpResultWeather<T>(
    @Json(name = "location") val location: DoubleArray,
    @Json(name = "result") val result: T
) : BaseBeanWeather() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HttpResultWeather<*>

        if (!location.contentEquals(other.location)) return false

        return true
    }

    override fun hashCode(): Int {
        return location.contentHashCode()
    }
}

data class RealTimeResponseBody(
    @Json(name = "apparent_temperature") val apparent_temperature: Double,
    @Json(name = "aqi") val aqi: Int,
    @Json(name = "cloudrate") val cloudrate: Double,
    @Json(name = "co") val co: Double,
    @Json(name = "comfort") val comfort: Comfort,
    @Json(name = "dswrf") val dswrf: Double,
    @Json(name = "humidity") val humidity: Double,
    @Json(name = "no2") val no2: Double,
    @Json(name = "o3") val o3: Double,
    @Json(name = "pm10") val pm10: Double,
    @Json(name = "pm25") val pm25: Double,
    @Json(name = "precipitation") val precipitation: Precipitation,
    @Json(name = "pres") val pres: Double,
    @Json(name = "skycon") val skycon: String,
    @Json(name = "so2") val so2: Double,
    @Json(name = "status") val status: String,
    @Json(name = "temperature") val temperature: Double,
    @Json(name = "ultraviolet") val ultraviolet: Ultraviolet,
    @Json(name = "visibility") val visibility: Double,
    @Json(name = "wind") val wind: Wind
)

data class Comfort(
    @Json(name = "desc") val desc: String,
    @Json(name = "index") val index: Int
)

data class Precipitation(
    @Json(name = "local") val local: Local,
    @Json(name = "nearest") val nearest: Nearest
)

data class Local(
    @Json(name = "datasource") val datasource: String,
    @Json(name = "intensity") val intensity: Double,
    @Json(name = "status") val status: String
)

data class Nearest(
    @Json(name = "distance") val distance: Double,
    @Json(name = "intensity") val intensity: Double,
    @Json(name = "status") val status: String
)

data class Ultraviolet(
    @Json(name = "desc") val desc: String,
    @Json(name = "index") val index: Int
)

data class Wind(
    @Json(name = "direction") val direction: Double,
    @Json(name = "speed") val speed: Double
)

data class ForecastResponseBody(
    @Json(name = "daily") val daily: Daily,
    @Json(name = "forecast_keypoint") val forecast_keypoint: String,
    @Json(name = "hourly") val hourly: Hourly,
    @Json(name = "minutely") val minutely: Minutely,
    @Json(name = "primary") val primary: Double
)

data class Minutely(
    @Json(name = "datasource") val datasource: String,
    @Json(name = "description") val description: String,
    @Json(name = "precipitation") val precipitation: DoubleArray,
    @Json(name = "precipitation_2h") val precipitation_2h: DoubleArray,
    @Json(name = "probability") val probability: DoubleArray,
    @Json(name = "probability_4h") val probability_4h: DoubleArray,
    @Json(name = "status") val status: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Minutely

        if (!precipitation.contentEquals(other.precipitation)) return false
        if (!precipitation_2h.contentEquals(other.precipitation_2h)) return false
        if (!probability.contentEquals(other.probability)) return false
        if (!probability_4h.contentEquals(other.probability_4h)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = precipitation.contentHashCode()
        result = 31 * result + precipitation_2h.contentHashCode()
        result = 31 * result + probability.contentHashCode()
        result = 31 * result + probability_4h.contentHashCode()
        return result
    }
}

data class Hourly(
    @Json(name = "aqi") val aqi: MutableList<Value>,
    @Json(name = "cloudrate") val cloudrate: MutableList<Value>,
    @Json(name = "description") val description: String,
    @Json(name = "dswrf") val dswrf: MutableList<Value>,
    @Json(name = "humidity") val humidity: MutableList<Value>,
    @Json(name = "pm25") val pm25: MutableList<Value>,
    @Json(name = "precipitation") val precipitation: MutableList<Value>,
    @Json(name = "pres") val pres: MutableList<Value>,
    @Json(name = "skycon") val skycon: MutableList<Skycon>,
    @Json(name = "status") val status: String,
    @Json(name = "temperature") val temperature: MutableList<Value>,
    @Json(name = "visibility") val visibility: MutableList<Value>,
    @Json(name = "wind") val wind: MutableList<WindHourly>
)

data class WindHourly(
    @Json(name = "datetime") val datetime: String,
    @Json(name = "direction") val direction: Double,
    @Json(name = "speed") val speed: Double
)

data class Value(
    @Json(name = "datetime") val datetime: String,
    @Json(name = "value") val value: Double
)

data class Daily(
    @Json(name = "aqi") val aqi: MutableList<Data>,
    @Json(name = "astro") val astro: MutableList<Astro>,
    @Json(name = "carWashing") val carWashing: MutableList<DataIndex>,
    @Json(name = "cloudrate") val cloudrate: MutableList<Data>,
    @Json(name = "coldRisk") val coldRisk: MutableList<DataIndex>,
    @Json(name = "comfort") val comfort: MutableList<DataIndex>,
    @Json(name = "dressing") val dressing: MutableList<DataIndex>,
    @Json(name = "dswrf") val dswrf: MutableList<Data>,
    @Json(name = "humidity") val humidity: MutableList<Data>,
    @Json(name = "pm25") val pm25: MutableList<Data>,
    @Json(name = "precipitation") val precipitation: MutableList<Data>,
    @Json(name = "pres") val pres: MutableList<Data>,
    @Json(name = "skycon") val skycon: MutableList<Skycon>,
    @Json(name = "skycon_08h_20h") val skycon_08h_20h: MutableList<Skycon>,
    @Json(name = "skycon_20h_32h") val skycon_20h_32h: MutableList<Skycon>,
    @Json(name = "status") val status: String,
    @Json(name = "temperature") val temperature: MutableList<Data>,
    @Json(name = "ultraviolet") val ultraviolet: MutableList<DataIndex>,
    @Json(name = "visibility") val visibility: MutableList<Data>,
    @Json(name = "wind") val wind: MutableList<WindForecast>
)

data class Data(
    @Json(name = "avg") val avg: Double,
    @Json(name = "date") val date: String,
    @Json(name = "max") val max: Double,
    @Json(name = "min") val min: Double
)

data class DataIndex(
    @Json(name = "datetime") val datetime: String,
    @Json(name = "desc") val desc: String,
    @Json(name = "index") val index: String
)

data class Astro(
    @Json(name = "date") val date: String,
    @Json(name = "sunrise") val sunrise: SunRiseSet,
    @Json(name = "sunset") val sunset: SunRiseSet
)

data class SunRiseSet(
    @Json(name = "time") val time: String
)

data class Skycon(
    @Json(name = "date") val date: String,
    @Json(name = "value") val value: String
)

data class WindForecast(
    @Json(name = "avg") val avg: Avg,
    @Json(name = "date") val date: String,
    @Json(name = "max") val max: Avg,
    @Json(name = "min") val min: Avg
)

data class Avg(
    @Json(name = "direction") val direction: Double,
    @Json(name = "speed") val speed: Double
)
