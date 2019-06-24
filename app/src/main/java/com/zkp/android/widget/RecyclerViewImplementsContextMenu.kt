package com.zkp.android.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.widget
 * @time: 2019/6/3 11:38
 * @description: 长按可弹出菜单的RecyclerView
 */
class RecyclerViewImplementsContextMenu : RecyclerView {

    private var contextMenuInfo: AdapterView.AdapterContextMenuInfo? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    public override fun getContextMenuInfo(): AdapterView.AdapterContextMenuInfo? {
        return contextMenuInfo
    }

    override fun showContextMenuForChild(originalView: View): Boolean {
        contextMenuInfo = AdapterView.AdapterContextMenuInfo(
            originalView,
            getChildAdapterPosition(originalView),
            getChildItemId(originalView)
        )
        return super.showContextMenuForChild(originalView)
    }

}