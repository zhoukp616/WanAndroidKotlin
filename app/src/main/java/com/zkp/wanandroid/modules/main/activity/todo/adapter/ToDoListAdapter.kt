package com.zkp.wanandroid.modules.main.activity.todo.adapter

import android.text.Html
import android.text.TextUtils
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zkp.wanandroid.R
import com.zkp.wanandroid.bean.ToDo

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.home.adapter
 * @time: 2019/5/28 15:16
 * @description:
 */
class ToDoListAdapter(layoutResId: Int, data: MutableList<ToDo>?) :

    BaseQuickAdapter<ToDo, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: ToDo?) {

        helper?.setText(R.id.tv_todo_title, Html.fromHtml(item?.title))
            ?.setText(R.id.tv_todo_content, item?.content)
        if (!TextUtils.isEmpty(item?.completeDateStr)) {
            helper?.setText(R.id.tv_todo_date, item?.completeDateStr)
        } else {
            helper?.setText(R.id.tv_todo_date, item?.dateStr)
        }

        if (item?.priority == 1) {
            helper?.getView<View>(R.id.tv_todo_priority)?.visibility = View.VISIBLE
        } else {
            helper?.getView<View>(R.id.tv_todo_priority)?.visibility = View.GONE
        }

    }
}