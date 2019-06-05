package com.zkp.android.http

import com.zkp.android.bean.*
import io.reactivex.Observable
import retrofit2.http.*

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
     * 退出登录
     * 访问了 logout 后，
     * 服务端会让客户端清除 Cookie（即cookie max-Age=0），
     * 如果客户端 Cookie 实现合理，可以实现自动清理，
     * 如果本地做了用户账号密码和保存，及时清理
     */
    @GET("user/logout/json")
    fun logout(): Observable<HttpResult<Any>>

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
    @POST("lg/collect/{id}/json")
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

    /**
     * 获取常用网站
     */
    @GET("/friend/json")
    fun getFriendJson(): Observable<HttpResult<MutableList<Friend>>>

    /**
     * 获取当前搜索最多的关键词
     *
     * @return
     */
    @GET("/hotkey/json")
    fun getHotKeys(): Observable<HttpResult<MutableList<Hotkey>>>

    /**
     * 搜索
     * 页码：拼接在链接上，从0开始
     * k ： 搜索关键词
     *
     * @param page int
     * @param k    String
     * @return
     */
    @POST("/article/query/{page}/json")
    fun searchArticlesByKeyWord(@Path("page") page: Int, @Query("k") k: String): Observable<HttpResult<ArticleResponseBody>>

    /**
     * 获取干货集中营福利相关图片
     * @param page 页码 从1开始
     */
    @GET("api/data/福利/10/{page}")
    fun getWelFare(@Path("page") page: Int): Observable<HttpResultGank<MutableList<WelFare>>>

    //=========================================================todo相关==========================================================
    /**
     * 获取todo列表
     * 页码从1开始，拼接在url 上
     * status 状态， 1-完成；0未完成; 默认全部展示；
     * type 创建时传入的类型, 默认全部展示
     * priority 创建时传入的优先级；默认全部展示
     * orderby 1:完成日期顺序；2.完成日期逆序；3.创建日期顺序；4.创建日期逆序(默认)；（1和2只能获取到已完成的TODO）
     *
     * @param page 页码 从1开始
     * @param map  参数列表
     * @return
     */
    @GET("/lg/todo/v2/list/{page}/json")
    fun getToDoList(@Path("page") page: Int, @QueryMap map: Map<String, Int>): Observable<HttpResult<ToDoResponseBody>>

    /**
     * 添加一条TODO数据
     * title: 新增标题
     * content: 新增详情
     * date: 2018-08-01
     * type: 0
     */
    @POST("lg/todo/add/json")
    @FormUrlEncoded
    fun addToDo(@FieldMap map: MutableMap<String, Any>): Observable<HttpResult<Any>>

    /**
     * 更新一条Todo内容
     * title: 新增标题
     * content: 新增详情
     * date: 2018-08-01
     * status: 0为未完成，1为完成
     * type: 0
     */
    @POST("/lg/todo/update/{id}/json")
    @FormUrlEncoded
    fun updateTodo(@Path("id") id: Int, @FieldMap map: MutableMap<String, Any>): Observable<HttpResult<Any>>

    /**
     * 删除一条ToDo
     * @param id todo事件的id
     */
    @POST("lg/todo/delete/{id}/json")
    fun deleteToDo(@Path("id") id: Int): Observable<HttpResult<Any>>

    /**
     * 仅更新todo事件的状态
     * @param id todo事件的id
     * @param status 0或1，传1代表未完成到已完成，反之则反之
     */
    @POST("lg/todo/done/{id}/json")
    fun updateToDoStatus(@Path("id") id: Int, @Query("status") status: Int): Observable<HttpResult<Any>>


}