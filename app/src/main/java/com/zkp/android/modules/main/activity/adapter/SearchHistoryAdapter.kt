package com.zkp.android.modules.main.activity.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zkp.android.R
import com.zkp.android.db.entity.SearchHistory

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.home.adapter
 * @time: 2019/5/28 15:16
 * @description:
 */
class SearchHistoryAdapter(layoutResId: Int, data: List<SearchHistory>?) :

    BaseQuickAdapter<SearchHistory, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: SearchHistory?) {
        helper?.setText(R.id.tv_search_key, item?.data)
            ?.addOnClickListener(R.id.iv_clear)
    }
}