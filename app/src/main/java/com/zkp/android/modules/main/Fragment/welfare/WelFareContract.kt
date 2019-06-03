package com.zkp.android.modules.main.Fragment.welfare

import android.content.Context
import android.graphics.Bitmap
import com.zkp.android.base.model.IModel
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView
import com.zkp.android.bean.HttpResultGank
import com.zkp.android.bean.WelFare
import io.reactivex.Observable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment.welfare
 * @time: 2019/6/3 9:20
 * @description:
 */
class WelFareContract {

    interface View : IView {

        /**
         * 获取福利相关图片成功
         * @param welfareList
         * @param isFresh 是否刷新
         */
        fun getWelFareSuccess(welfareList: MutableList<WelFare>, isFresh: Boolean)

        /**
         * 获取福利相关图片失败
         * @param errorMsg
         */
        fun getWelFareError(errorMsg: String)

        /**
         * 保存图片成功
         *
         * @param path 图片路径
         */
        fun saveBitmapSuccess(path: String)

        /**
         * 保存图片失败
         *
         * @param errMsg String
         */
        fun saveBitmapError(errMsg: String)

    }

    interface Presenter : IPresenter<View> {

        /**
         * 获取福利相关图片
         * @param page 页码 从1开始
         * @param isFresh 是否刷新
         */
        fun getWelFare(page: Int, isFresh: Boolean)

        /**
         * 刷新
         */
        fun refresh()

        /**
         * 加载更多
         */
        fun loadMore()

        /**
         * 保存图片到手机
         *
         * @param bitmap
         * @param filePath 保存路径
         * @param context
         */
        fun saveBitmap(bitmap: Bitmap, filePath: String, context: Context)

    }

    interface Model : IModel {

        /**
         * 请求获取福利相关图片
         * @param page 页码 从1开始
         */
        fun requestWelfare(page: Int): Observable<HttpResultGank<MutableList<WelFare>>>

    }

}