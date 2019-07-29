package com.zkp.wanandroid.modules.knowledge.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zkp.wanandroid.R
import com.zkp.wanandroid.bean.KnowledgeTreeBody

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.knowledge.adapter
 * @time: 2019/5/29 14:35
 * @description:
 */
class KnowledgeAdapter(layoutResId: Int, data: MutableList<KnowledgeTreeBody>?) :

    BaseQuickAdapter<KnowledgeTreeBody, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: KnowledgeTreeBody?) {
        if (item?.name == null) {
            return
        }
        helper?.setText(R.id.knowledge_title, item.name)

        item.children.let {
            helper?.setText(R.id.title_child,
                it.joinToString("    ", transform = { child ->
                    Html.fromHtml(child.name)
                })
            )

        }
    }
}