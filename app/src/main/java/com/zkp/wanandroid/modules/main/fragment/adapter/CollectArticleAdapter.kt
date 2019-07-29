package com.zkp.wanandroid.modules.main.fragment.adapter

import android.text.Html
import android.text.TextUtils
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zkp.wanandroid.R
import com.zkp.wanandroid.bean.CollectArticle
import com.zkp.wanandroid.utils.ImageLoader

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.home.adapter
 * @time: 2019/5/28 15:16
 * @description:
 */
class CollectArticleAdapter(layoutResId: Int, data: MutableList<CollectArticle>?) :

    BaseQuickAdapter<CollectArticle, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: CollectArticle?) {

        helper?.setText(R.id.tvArticleTitle, Html.fromHtml(item?.title))
            ?.setText(R.id.tvArticleAuthor, item?.author)
            ?.setImageResource(R.id.ivArticleLike, R.drawable.ic_like)
        if (!TextUtils.isEmpty(item?.chapterName)) {
            helper?.setText(R.id.tvArticleChapterName, item?.chapterName)
        }
        if (!TextUtils.isEmpty(item?.niceDate)) {
            helper?.setText(R.id.tvArticleNiceDate, item?.niceDate)
        }

        if (!TextUtils.isEmpty(item?.envelopePic)) {
            helper?.getView<View>(R.id.ivArticleThumbnail)?.visibility = View.VISIBLE
            ImageLoader.load(mContext, item?.envelopePic, helper?.getView(R.id.ivArticleThumbnail))
        } else {
            helper?.getView<View>(R.id.ivArticleThumbnail)?.visibility = View.GONE
        }

        helper?.addOnClickListener(R.id.ivArticleLike)

    }
}