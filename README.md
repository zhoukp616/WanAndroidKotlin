## 玩安卓客户端 Kotlin版 ##

[![Platform][1]][2]  [![Release][3]][4]  [![Release][5]][6]  [![][7]][8] 

[1]:https://img.shields.io/badge/platform-Android-blue.svg  
[2]:https://github.com/Zkp275557625/WanAndroidKotlin

[3]:https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat
[4]:https://android-arsenal.com/api?level=21

[5]:https://img.shields.io/github/release/Zkp275557625/WanAndroidKotlin.svg
[6]:https://github.com/Zkp275557625/WanAndroidKotlin/releases/latest

[7]:https://img.shields.io/badge/QQ-275557625-blue.svg
[8]:http://wpa.qq.com/msgrd?v=3&uin=275557625&site=qq&menu=yes

数据主要来源：[玩安卓](https://www.wanandroid.com)

### 1.首页 ###

![](https://i.imgur.com/vnxYa3G.jpg)

### 2.知识体系 ###

![](https://i.imgur.com/sxB4VVL.jpg)

![](https://i.imgur.com/1rs5qKd.jpg)

### 3.微信公众号 ###

![](https://i.imgur.com/O30v4oG.jpg)

### 4.导航 ###

![](https://i.imgur.com/GjxO73N.jpg)

### 5.项目 ###

![](https://i.imgur.com/R7Ouyra.jpg)

### 6.搜索 ###

![](https://i.imgur.com/MelHVja.jpg)

![](https://i.imgur.com/esALcRO.jpg)

![](https://i.imgur.com/wIzmLar.jpg)

### 7.常用网站 ###

![](https://i.imgur.com/nbhG3dx.jpg)

### 8.文章详情页面 ###

![](https://i.imgur.com/ApPW9gq.jpg)

### 9.登录、注册页面 ###

![](https://i.imgur.com/VqFQO4s.jpg)

![](https://i.imgur.com/SyHM7Uq.jpg)

### 10.收藏页面 ###

![](https://i.imgur.com/QMxissw.jpg)

### 11.干货页面(此页面数据来源：[干货集中营](https://gank.io/api)) ###

![](https://i.imgur.com/uIIztph.jpg)

### 12.天气页面 ###

![](https://i.imgur.com/ivbazgQ.jpg)

![](https://i.imgur.com/1vebnHj.jpg)

![](https://i.imgur.com/6szOpBm.jpg)

本页面数据来源：[彩云天气](http://wiki.swarma.net/index.php/%E5%BD%A9%E4%BA%91%E5%A4%A9%E6%B0%94API/v2)

用到的接口：

- 实时天气
- 小时级天气
- 天级天气

其中还用到了百度地图SDK：

- 进入页面是会启动定位，选择**当前区/县**作为本地天气，该数据会存储在本地数据库中
- 选择其他地区后该地区信息会添加到数据库中，不包含经纬度信息，第一次获取改地区天气的时候会使用**百度地图的[地理编码](http://lbsyun.baidu.com/index.php?title=androidsdk/guide/search/geo)**转换经纬度并更新本地数据库

### 13.TODO页面 ###

![](https://i.imgur.com/pNBZDRa.jpg)

### 14.夜间模式 ###

![](https://i.imgur.com/a3QlWry.jpg)

### 15.设置页面 ###

![](https://i.imgur.com/AxarmTT.jpg)

### 16.关于页面 ###

![](https://i.imgur.com/cBTiuKx.jpg)

![](https://i.imgur.com/SqKzNhP.jpg)

![](https://i.imgur.com/FRfCuru.jpg)

其中版本更新页面的api使用[模客](http://mock-api.com/app.html)，安装包包含在源码中。

### 17.测试博客 ###
该页面内容主要为学习软件测试的知识

![](https://i.imgur.com/yTBJmUz.jpg)

该页面数据来源：[老_张](https://www.cnblogs.com/imyalost/category/873684.html)

2019-05-17 添加测试博客收藏功能---对应玩安卓开放api中的收藏站外文章

# 声明 #

项目中的API均不是自己编写，**仅供交流学习使用**，不得用于商业用途。
另外项目中使用百度AK和彩云天气AK均是我自己申请的，下载后**请立即更**换为自己申请的AK，**相信大家都是有素养的程序员**