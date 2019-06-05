package com.zkp.android.modules.main.fragment.adapter

import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zkp.android.R
import com.zkp.android.bean.WelFare
import com.zkp.android.utils.ImageLoader

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