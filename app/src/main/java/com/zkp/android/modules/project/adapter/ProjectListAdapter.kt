package com.zkp.android.modules.project.adapter

import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zkp.android.R
import com.zkp.android.bean.Article
import com.zkp.android.utils.ImageLoader

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.knowledge.adapter
 * @time: 2019/5/29 14:35
 * @description:
 */
class ProjectListAdapter(layoutResId: Int, data: MutableList<Article>?) :

    BaseQuickAdapter<Article, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: Article?) {

        helper?.setText(R.id.tvArticleTitle, Html.fromHtml(item?.title))
            ?.setText(R.id.tvArticleAuthor, item?.author)
            ?.setImageResource(R.id.ivArticleLike, if (item!!.collect) R.drawable.ic_like else R.drawable.ic_like_not)

        helper?.setText(R.id.tvArticleChapterName, item?.superChapterName + " / " + item?.chapterName)
        helper?.setText(R.id.tvArticleNiceDate, item?.niceDate)

        helper?.getView<View>(R.id.tvArticleTop)?.visibility = if (item?.type == 1) View.VISIBLE else View.GONE

        helper?.getView<View>(R.id.tvArticleFresh)?.visibility = if (item!!.fresh) View.VISIBLE else View.GONE

        if (item.tags.size > 0) {
            helper?.setText(R.id.tvArticleTag, item.tags[0].name)?.getView<TextView>(R.id.tvArticleTag)?.visibility =
                View.VISIBLE
        } else {
            helper?.getView<View>(R.id.tvArticleTag)?.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(item.envelopePic)) {
            helper?.getView<View>(R.id.ivArticleThumbnail)?.visibility = View.VISIBLE
            mContext!!.let {
                ImageLoader.load(it, item.envelopePic, helper?.getView(R.id.ivArticleThumbnail))
            }
        } else {
            helper?.getView<View>(R.id.ivArticleThumbnail)?.visibility = View.GONE
        }

        helper?.addOnClickListener(R.id.ivArticleLike)

    }
}