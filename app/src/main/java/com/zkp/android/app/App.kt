package com.zkp.android.app

import android.app.Application
import android.content.Context
import com.coder.zzq.smartshow.core.SmartShow
import com.sunchen.netbus.NetStatusBus
import com.zkp.android.db.greendao.DaoMaster
import com.zkp.android.db.greendao.DaoSession
import com.zkp.android.http.AppConfig
import kotlin.properties.Delegates

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.app
 * @time: 2019/5/27 14:32
 * @description:
 */
class App : Application() {

    //Kotlin中没有static类型，只能用伴生对象
    companion object {

        var mContext: Context by Delegates.notNull()

        fun getContext(): Context {
            return mContext
        }

        var mDaoSession: DaoSession by Delegates.notNull()

        fun getDaoSession(): DaoSession {
            return mDaoSession
        }

    }

    override fun onCreate() {
        super.onCreate()
        mContext = this

        initGreenDao()

        SmartShow.init(this)
        NetStatusBus.getInstance().init(this)
    }

    private fun initGreenDao() {
        val devOpenHelper = DaoMaster.DevOpenHelper(this, AppConfig().DB_NAME)
        val database = devOpenHelper.writableDatabase
        val daoMaster = DaoMaster(database)
        mDaoSession = daoMaster.newSession()
    }

}