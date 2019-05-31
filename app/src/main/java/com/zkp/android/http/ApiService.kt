package com.zkp.android.http

import com.zkp.android.bean.*
import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.http.POST
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
    fun getKnowledgeList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): Observable<HttpResult<ArticleResponseBody>>

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
    fun getWeChatArticle(
        @Path("id") id: Int,
        @Path("page") page: Int
    ): Observable<HttpResult<ArticleResponseBody>>

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
    fun getProjectList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): Observable<HttpResult<ArticleResponseBody>>

    /**
     * 登录
     * 登录后会在cookie中返回账号密码，只要在客户端做cookie持久化存储即可自动登录验证
     * @param username 用户名
     * @param password 密码
     */
    @POST("user/login")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Observable<HttpResult<Login>>

    /**
     * 注册
     * @param username 用户名
     * @param password 密码
     * @param repassword 重复密码
     */
    @POST("user/register")
    fun register(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("repassword") repassword: String
    ): Observable<HttpResult<Login>>

    /**
     * 获取收藏的文章列表
     * @param page 页码 从0开始
     */
    @GET("lg/collect/list/{page}/json")
    fun getCollectList(@Path("page") page: Int): Observable<HttpResult<CollectResponseBody<CollectArticle>>>

    /**
     * 收藏站内文章
     * @param id 文章id
     */
    @POST("lg/collect/(id)/json")
    fun collectArticle(@Path("id") id: Int): Observable<HttpResult<Any>>

    /**
     * 收藏站外文章
     */
    @POST("lg/collect/add/json")
    fun collectArticleOutside(
        @Query("title") title: String,
        @Query("author") author: String,
        @Query("link") link: String
    ): Observable<HttpResult<Any>>

    /**
     * 取消收藏 --文章列表
     *
     * @param id int
     */
    @POST("/lg/uncollect_originId/{id}/json")
    fun unCollectArticle(@Path("id") id: Int): Observable<HttpResult<Any>>

    /**
     * 取消收藏
     * id:拼接在链接上
     * originId:列表页下发，无则为-1
     *
     * @param id       int
     * @param originId int
     */
    @POST("/lg/uncollect/{id}/json")
    fun unCollectInCollectPage(
        @Path("id") id: Int,
        @Query("originId") originId: Int
    ): Observable<HttpResult<Any>>


}