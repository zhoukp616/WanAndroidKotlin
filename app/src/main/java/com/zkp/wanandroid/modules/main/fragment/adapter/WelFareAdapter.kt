package com.zkp.wanandroid.modules.main.fragment.adapter

import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zkp.wanandroid.R
import com.zkp.wanandroid.bean.WelFare
import com.zkp.wanandroid.utils.ImageLoader

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.home.adapter
 * @time: 2019/5/28 15:16
 * @description:
 */
class WelFareAdapter(layoutResId: Int, data: MutableList<WelFare>?) :

    BaseQuickAdapter<WelFare, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: WelFare?) {

        if (!TextUtils.isEmpty(item?.url)) {
            ImageLoader.load(mContext, item?.url, helper?.getView(R.id.imageView))
        }

        //短按全屏显示
        helper?.addOnClickListener(R.id.imageView)
        //长按保存图片
        helper?.addOnLongClickListener(R.id.imageView)

    }
}