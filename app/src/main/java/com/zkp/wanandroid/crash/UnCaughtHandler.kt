package com.zkp.wanandroid.crash

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import com.coder.zzq.smartshow.toast.SmartToast
import com.zkp.wanandroid.BuildConfig
import com.zkp.wanandroid.R
import com.zkp.wanandroid.app.App
import com.zkp.wanandroid.modules.main.MainActivity
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.crash
 * @time: 2019/6/10 14:02
 * @description:
 */
class UnCaughtHandler : Thread.UncaughtExceptionHandler {

    /**
     * 错误报告文件的扩展名
     */
    private val CRASH_REPORTER_EXTENSION = ".txt"
    private lateinit var mApplication: App
    /**
     * 默认Exception Handler
     */
    private lateinit var mDefaultHandler: Thread.UncaughtExceptionHandler

    fun unCaughtHandler(application: App): UnCaughtHandler {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        this.mApplication = application
        return this
    }

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        if (e == null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(t, null)
        } else {
            val intent = Intent(mApplication.applicationContext, MainActivity::class.java)
            @SuppressLint("WrongConstant")
            val restartIntent = PendingIntent.getActivity(
                mApplication.applicationContext,
                0, intent, Intent.FLAG_ACTIVITY_NEW_TASK
            )

            //收集设备信息
            //保存错误报告文件
            saveCrashInfoToFile(e)

            //退出App后再重启App
            val alarmManager = mApplication.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 3000, restartIntent)
            SmartToast.warning("应用将在3秒后重启")
            mApplication.exitApplication()
        }
    }

    /**
     * 错误信息存储到文本并保存到手机中
     *
     * @param ex Throwable
     */
    private fun saveCrashInfoToFile(ex: Throwable) {
        val info = StringWriter()
        val printWriter = PrintWriter(info)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        val result = info.toString()
        printWriter.close()
        val sb = StringBuilder()
        @SuppressLint("SimpleDateFormat") val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        val now = sdf.format(Date())
        //崩溃时间
        sb.append("TIME:").append(now)
        //程序信息
        //软件APPLICATION_ID
        sb.append("\nAPPLICATION_ID:").append(BuildConfig.APPLICATION_ID)
        //软件版本号
        sb.append("\nVERSION_CODE:").append(BuildConfig.VERSION_CODE)
        //VERSION_NAME
        sb.append("\nVERSION_NAME:").append(BuildConfig.VERSION_NAME)
        //是否是DEBUG版本
        sb.append("\nBUILD_TYPE:").append(BuildConfig.BUILD_TYPE)
        //设备信息
        sb.append("\nMODEL:").append(Build.MODEL)
        sb.append("\nRELEASE:").append(Build.VERSION.RELEASE)
        sb.append("\nSDK:").append(Build.VERSION.SDK_INT)
        sb.append("\nEXCEPTION:").append(ex.localizedMessage)
        sb.append("\nSTACK_TRACE:").append(result)
        try {
            val writer = FileWriter(getCrashFilePath(mApplication.applicationContext) + now + CRASH_REPORTER_EXTENSION)
            writer.write(sb.toString())
            writer.flush()
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 获取文件夹路径
     *
     * @param context 上下文
     * @return 文件夹路径
     */
    private fun getCrashFilePath(context: Context): String {
        val path =
            Environment.getExternalStorageDirectory().toString() + File.separator + context.resources.getString(R.string.app_name) + "/Crash/"
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        return path
    }

}