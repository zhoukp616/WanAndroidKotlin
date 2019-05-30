package com.zkp.android.modules.navigation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import com.zkp.android.R
import com.zkp.android.bean.Article
import com.zkp.android.bean.Navigation
import com.zkp.android.modules.home.detail.ArticleDetailActivity

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.home.adapter
 * @time: 2019/5/28 15:16
 * @description:
 */
class NavigationAdapter(layoutResId: Int, data: MutableList<Navigation>?) :

    BaseQuickAdapter<Navigation, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: Navigation?) {
        helper?.setText(R.id.tvCategory, item?.name)

        val tagFlowLayout = helper?.getView<TagFlowLayout>(R.id.tagFlowLayout)

        tagFlowLayout?.adapter = object : TagAdapter<Article>(item?.articles) {
            override fun getView(
                parent: FlowLayout,
                position: Int,
                article: Article?
            ): View? {
                val textView = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_flow_layout,
                    tagFlowLayout,
                    false
                ) as TextView
                textView.text = article?.title
                tagFlowLayout?.setOnTagClickListener { _, position1, _ ->
                    val intent = Intent(parent.context, ArticleDetailActivity::class.java)
                    intent.putExtra("title", item?.articles?.get(position1)?.link)
                    intent.putExtra("articleLink", item?.articles?.get(position1)?.link)
                    intent.putExtra("articleId", item?.articles?.get(position1)?.id)
                    intent.putExtra("isCollected", item?.articles?.get(position1)?.collect)
                    intent.putExtra("isShowCollectIcon", true)
                    intent.putExtra("articleItemPosition", position1)
                    (parent.context).startActivity(intent)
                    true
                }
                return textView
            }
        }

    }
}