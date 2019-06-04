package com.zkp.android.modules.main.Fragment.about

import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.coder.zzq.smartshow.dialog.NotificationDialog
import com.vector.update_app.UpdateAppManager
import com.zkp.android.R
import com.zkp.android.app.App
import com.zkp.android.base.fragment.BaseFragment
import com.zkp.android.http.AppConfig
import com.zkp.android.http.UpdateAppHttpUtil
import com.zkp.android.modules.home.detail.ArticleDetailActivity

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment.about
 * @time: 2019/6/4 13:43
 * @description:
 */
class AboutUsFragment : BaseFragment<AboutUsContract.View, AboutUsContract.Presenter>(), AboutUsContract.View {

    @BindView(R.id.about_version)
    lateinit var mAboutVersion: TextView

    private lateinit var mNotificationDialog: NotificationDialog

    override fun createPresenter(): AboutUsContract.Presenter = AboutUsPresenter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_about
    }

    override fun initView() {
        try {
            val versionStr = (getString(R.string.app_name)
                    + " V" + App.getContext().packageManager
                .getPackageInfo(App.getContext().packageName, 0).versionName)
            mAboutVersion.text = versionStr
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun initEventAndData() {
    }

    @OnClick(
        R.id.about_update,
        R.id.about_website,
        R.id.about_source_code,
        R.id.about_feedback,
        R.id.about_copyright
    )
    internal fun onClick(view: View) {
        val intent: Intent
        when (view.id) {
            R.id.about_update ->
                //检查更新
                UpdateAppManager.Builder()
                    //当前Activity
                    .setActivity(_mActivity)
                    //更新地址
                    .setUpdateUrl(AppConfig().URL_UPDATE)
                    //实现httpManager接口的对象
                    .setHttpManager(UpdateAppHttpUtil())
                    .setTopPic(R.drawable.update_dialog_bg)
                    .setThemeColor(R.color.colorPrimary)
                    .build()
                    .update()
            R.id.about_website -> {
                //关于网站
                intent = Intent(activity, ArticleDetailActivity::class.java)
                intent.putExtra("articleLink", AppConfig().ABOUT_WEBSITE)
                intent.putExtra("articleId", -1)
                intent.putExtra("isCollected", false)
                intent.putExtra("isShowCollectIcon", false)
                intent.putExtra("articleItemPosition", -1)
                activity?.startActivity(intent)
            }
            R.id.about_source_code -> {
                //源码下载
                intent = Intent(activity, ArticleDetailActivity::class.java)
                intent.putExtra("articleLink", AppConfig().ABOUT_SOURCE_CODE)
                intent.putExtra("articleId", -1)
                intent.putExtra("isCollected", false)
                intent.putExtra("isShowCollectIcon", false)
                intent.putExtra("articleItemPosition", -1)
                activity?.startActivity(intent)
            }
            R.id.about_feedback -> {
                //意见反馈
                intent = Intent(activity, ArticleDetailActivity::class.java)
                intent.putExtra("articleLink", AppConfig().ABOUT_FEEDBACK)
                intent.putExtra("articleId", -1)
                intent.putExtra("isCollected", false)
                intent.putExtra("isShowCollectIcon", false)
                intent.putExtra("articleItemPosition", -1)
                activity?.startActivity(intent)
            }
            R.id.about_copyright ->
                //版权声明
                onShowNotificationDialog()
        }
    }

    private fun onShowNotificationDialog() {
        mNotificationDialog = NotificationDialog()
            .title("版权声明")
            .message("本APP完全开源，仅供学习、交流使用，严禁用于商业用途，违者后果自负。")
        mNotificationDialog.showInActivity(activity)
    }

}