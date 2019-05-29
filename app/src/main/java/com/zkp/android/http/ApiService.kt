package com.zkp.android.http

import com.zkp.android.bean.ArticleResponseBody
import com.zkp.android.bean.Banner
import com.zkp.android.bean.HttpResult
import com.zkp.android.bean.KnowledgeTreeBody
import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.http
 * @time: 2019/5/28 14:26
 * @description:
 */
interface ApiService {

    /**
     * 获取首页文章列表
     * @param page 页码 从0开始
     */
    @GET("article/list/{page}/json")
    fun getHomeArticleList(@Path("page") page: Int): Observable<HttpResult<ArticleResponseBody>>

    /**
     * 获取首页轮播图
     */
    @GET("banner/json")
    fun getBanner(): Observable<HttpResult<List<Banner>>>

    /**
     * 获取知识体系数据
     */
    @GET("tree/json")
    fun getKnowledgeTree(): Observable<HttpResult<List<KnowledgeTreeBody>>>

    /**
     * 获取知识体系下的文章
     * @param page 页码 从0开始
     * @param cid 二级目录的id
     */
    @GET("/article/list/{page}/json?")
    fun getKnowledgeList(@Path("page") page: Int, @Query("cid") cid: Int): Observable<HttpResult<ArticleResponseBody>>

}