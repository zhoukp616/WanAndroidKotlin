package com.zkp.android.modules.main.fragment.usage

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.coder.zzq.smartshow.toast.SmartToast
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import com.zkp.android.R
import com.zkp.android.base.fragment.BaseFragment
import com.zkp.android.bean.Friend
import com.zkp.android.modules.home.detail.ArticleDetailActivity

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment.usage
 * @time: 2019/5/31 17:33
 * @description:
 */
class UseageFragment : BaseFragment<UsegeContract.View, UsegeContract.Presenter>(), UsegeContract.View {

    @BindView(R.id.tagFlowLayout)
    internal lateinit var mTagFlowLayout: TagFlowLayout

    override fun createPresenter(): UsegeContract.Presenter = UseagePresenter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_useage
    }

    override fun initView() {
    }

    override fun initEventAndData() {
        mPresenter?.getFriendJson()
    }

    override fun getFriendJsonSuccess(friendList: MutableList<Friend>) {

        mTagFlowLayout.adapter = object : TagAdapter<Friend>(friendList) {
            override fun getView(parent: FlowLayout, position: Int, dataBean: Friend?): View {
                val textView = LayoutInflater.from(_mActivity)
                    .inflate(R.layout.item_flow_layout, parent, false) as TextView
                if (dataBean != null) {
                    textView.text = dataBean.name
                }
                return textView
            }
        }

        mTagFlowLayout.setOnTagClickListener { _, position, parent ->

            val intent = Intent(parent.context, ArticleDetailActivity::class.java)
            intent.putExtra("title", friendList[position].name)
            intent.putExtra("articleLink", friendList[position].link)
            intent.putExtra("articleId", friendList[position].id)
            intent.putExtra("isCollected", false)
            intent.putExtra("isShowCollectIcon", false)
            intent.putExtra("articleItemPosition", -1)
            parent.context.startActivity(intent)

            true
        }

    }

    override fun getFriendJsonError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }
}