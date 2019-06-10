package com.zkp.android.app

import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatDelegate
import com.coder.zzq.smartshow.core.SmartShow
import com.sunchen.netbus.NetStatusBus
import com.zkp.android.base.activity.AbstractSimpleActivity
import com.zkp.android.base.activity.BaseActivity
import com.zkp.android.base.fragment.AbstractSimpleFragment
import com.zkp.android.base.fragment.BaseFragment
import com.zkp.android.crash.UnCaughtHandler
import com.zkp.android.db.greendao.DaoMaster
import com.zkp.android.db.greendao.DaoSession
import com.zkp.android.http.AppConfig
import com.zkp.android.utils.SpUtils
import java.util.*
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

        lateinit var mActivityList: MutableList<AbstractSimpleActivity>
        lateinit var mFragmentsList: MutableList<AbstractSimpleFragment>

    }

    override fun onCreate() {
        super.onCreate()
        mContext = this

        mActivityList = mutableListOf()
        mFragmentsList = mutableListOf()

        initGreenDao()

        SmartShow.init(this)
        NetStatusBus.getInstance().init(this)

        if (SpUtils().getBoolean(this, "isNightMode")) {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )
        } else {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )
        }

    }

    private fun initGreenDao() {
        val devOpenHelper = DaoMaster.DevOpenHelper(this, AppConfig().DB_NAME)
        val database = devOpenHelper.writableDatabase
        val daoMaster = DaoMaster(database)
        mDaoSession = daoMaster.newSession()
    }

    /**
     * 闪退重启
     */
    fun initUnCaughtHandler() {
        Thread.setDefaultUncaughtExceptionHandler(UnCaughtHandler().unCaughtHandler(this))
    }

    fun addActivity(activity: AbstractSimpleActivity) {
        mActivityList.add(activity)
    }

    fun addFragment(fragment: AbstractSimpleFragment) {
        mFragmentsList.add(fragment)
    }

    /**
     * 退出应用
     */
    fun exitApplication() {
        for (activity in mActivityList) {
            activity.finish()
        }
        for (fragment in mFragmentsList) {
            fragment.activity?.onBackPressed()
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid())
    }

}