package com.zkp.android.modules.main.Fragment.welfare

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.zkp.android.base.presenter.BasePresenter
import com.zkp.android.bean.HttpResultGank
import com.zkp.android.bean.WelFare
import com.zkp.android.http.HttpUtil
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment.welfare
 * @time: 2019/6/3 9:21
 * @description:
 */
class WelfarePresenter : BasePresenter<WelFareContract.Model, WelFareContract.View>(), WelFareContract.Presenter {

    private var currentPage: Int = 1

    override fun createModel(): WelFareContract.Model? = WelfareModel()


    override fun getWelFare(page: Int, isFresh: Boolean) {
        mView?.showLoading()
        HttpUtil().request(mModel!!.requestWelfare(page),
            object : HttpUtil.IResponseListener<HttpResultGank<MutableList<WelFare>>> {
                override fun onSuccess(data: HttpResultGank<MutableList<WelFare>>) {
                    if (!data.error) {
                        mView?.getWelFareSuccess(data.results, isFresh)
                    } else {
                        mView?.getWelFareError("获取福利图片失败")
                    }
                    mView?.hideLoading()
                }

                override fun onFail(errMsg: String) {
                    mView?.getWelFareError(errMsg)
                    mView?.hideLoading()
                }

            })
    }

    override fun refresh() {
        this.currentPage = 1
        getWelFare(currentPage, true)
    }

    override fun loadMore() {
        this.currentPage++
        getWelFare(currentPage, false)
    }

    override fun saveBitmap(bitmap: Bitmap, filePath: String, context: Context) {

        if (mView != null) {
            mView?.showLoading()

            val fileName = generateFileName()
            val file = File(filePath, fileName)
            if (file.exists()){
                file.delete()
            }
            if (!file.parentFile.exists()){
                file.parentFile.mkdirs()
            }

            try {
                val outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
                outputStream.flush()
                outputStream.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                mView?.saveBitmapError("保存失败")
                mView?.hideLoading()
            } catch (e: IOException) {
                e.printStackTrace()
                mView?.saveBitmapError("保存失败")
                mView?.hideLoading()
            }

            //其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(
                    context.contentResolver,
                    file.absolutePath, file.name, null
                )
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                mView?.saveBitmapError("保存失败")
                mView?.hideLoading()
            }

            //最后通知图库更新
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val uri = Uri.fromFile(file)
            intent.data = uri
            context.sendBroadcast(intent)

            mView?.saveBitmapSuccess(file.absolutePath)
            mView?.hideLoading()

        }
    }

    /**
     * 生成文件名
     *
     * @return
     */
    private fun generateFileName(): String {
        @SuppressLint("SimpleDateFormat")
        val formatter = SimpleDateFormat("yyyyMMdd_HHmmss")
        //获取当前时间
        val curDate = Date(System.currentTimeMillis())
        return "IMG_" + formatter.format(curDate) + ".jpg"
    }

}