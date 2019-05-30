package com.zkp.android.http

import com.zkp.android.bean.*
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
    fun getBanner(): Observable<HttpResult<MutableList<Banner>>>

    /**
     * 获取知识体系数据
     */
    @GET("tree/json")
    fun getKnowledgeTree(): Observable<HttpResult<MutableList<KnowledgeTreeBody>>>

    /**
     * 获取知识体系下的文章
     * @param page 页码 从0开始
     * @param cid 二级目录的id
     */
    @GET("/article/list/{page}/json?")
    fun getKnowledgeList(@Path("page") page: Int, @Query("cid") cid: Int): Observable<HttpResult<ArticleResponseBody>>

    /**
     * 获取微信公众号列表
     */
    @GET("wxarticle/chapters/json ")
    fun getWeChatChapters(): Observable<HttpResult<MutableList<ProjectTree>>>

    /**
     * 获取某个微信公众号的文章列表
     * @param id 微信公众号id
     * @param page 页码 从1开始
     */
    @GET("wxarticle/list/{id}/{page}/json")
    fun getWeChatArticle(@Path("id") id: Int, @Path("page") page: Int): Observable<HttpResult<ArticleResponseBody>>

    /**
     * 获取导航数据
     */
    @GET("navi/json")
    fun getNaviJson(): Observable<HttpResult<MutableList<Navigation>>>

    /**
     * 获取项目分类数据
     */
    @GET("project/tree/json")
    fun getProjectTree(): Observable<HttpResult<MutableList<ProjectTree>>>

    /**
     * 获取某个分类下的项目列表
     * @param page 页码 从1开始
     * @param cid 项目分类id
     */
    @GET("project/list/{page}/json?")
    fun getProjectList(@Path("page") page: Int, @Query("cid") cid: Int): Observable<HttpResult<ArticleResponseBody>>

}