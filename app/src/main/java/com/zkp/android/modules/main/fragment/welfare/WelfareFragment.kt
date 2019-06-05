package com.zkp.android.modules.main.fragment.welfare

import android.content.Intent
import android.support.v4.content.FileProvider
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import com.coder.zzq.smartshow.dialog.EnsureDialog
import com.coder.zzq.smartshow.toast.SmartToast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import com.zkp.android.R
import com.zkp.android.base.fragment.BaseFragment
import com.zkp.android.bean.WelFare
import com.zkp.android.http.AppConfig
import com.zkp.android.modules.main.fragment.adapter.WelFareAdapter
import java.io.File

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment.welfare
 * @time: 2019/6/3 9:23
 * @description:
 */
class WelfareFragment : BaseFragment<WelFareContract.View, WelFareContract.Presenter>(), WelFareContract.View {

    @BindView(R.id.refreshLayout)
    lateinit var mRefreshLayout: SmartRefreshLayout

    @BindView(R.id.recyclerView)
    lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapter: WelFareAdapter
    private lateinit var mEnsureDialog: EnsureDialog

    override fun createPresenter(): WelFareContract.Presenter = WelfarePresenter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_welfare
    }

    override fun initView() {

        mRecyclerView.layoutManager = GridLayoutManager(context, 1)
        mRecyclerView.setHasFixedSize(true)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(mRecyclerView)

        val welFareList: MutableList<WelFare> = mutableListOf()
        mAdapter = WelFareAdapter(R.layout.item_welfare_list, welFareList)
        mRecyclerView.adapter = mAdapter

    }

    override fun initEventAndData() {

        initRefreshLayout()

        mAdapter.setOnItemChildLongClickListener { adapter, view, position ->
            //ImageView点击事件处理
            AndPermission.with(activity)
                .runtime()
                .permission(*Permission.Group.STORAGE)
                .onGranted {
                    //保存图片到手机
                    showEnsureDialog(view)

                }
                .onDenied {
                    AndPermission.with(activity)
                        .runtime()
                        .permission(*Permission.Group.STORAGE)
                        .start()
                }
                .start()
            true
        }

        mPresenter?.refresh()
    }

    private fun showEnsureDialog(view: View) {
        mEnsureDialog = EnsureDialog()
            .message("确定要保存这张图片到手机吗？")
            .confirmBtn("确定") { dialog, _, _ ->
                dialog.dismiss()
                val imageView = view as ImageView
                imageView.isDrawingCacheEnabled = true
                mPresenter?.saveBitmap(imageView.drawingCache, AppConfig().APP_PATH, activity!!)
                imageView.isDrawingCacheEnabled = false
            }
        mEnsureDialog.showInActivity(activity)
    }

    private fun initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener { refreshLayout ->
            mPresenter?.refresh()
            refreshLayout.finishRefresh()
        }

        mRefreshLayout.setOnLoadMoreListener { refreshLayout ->
            mPresenter?.loadMore()
            refreshLayout.finishLoadMore()
        }
    }

    override fun getWelFareSuccess(welfareList: MutableList<WelFare>, isFresh: Boolean) {
        if (isFresh) {
            mAdapter.replaceData(welfareList)
        } else {
            mAdapter.addData(welfareList)
        }
    }

    override fun getWelFareError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun saveBitmapSuccess(path: String) {
        //保存图片成功，询问用户是否打开
        mEnsureDialog = EnsureDialog()
            .message("现在查看打开这张图片吗？")
            .confirmBtn("确定") { dialog, _, _ ->
                dialog.dismiss()

                val intent = Intent(Intent.ACTION_VIEW)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setDataAndType(
                    FileProvider.getUriForFile(
                        activity!!,
                        "com.zkp.androiddev.provider",
                        File(path)
                    ), "image/*"
                )
                startActivity(intent)

            }
        mEnsureDialog.showInActivity(activity)
    }

    override fun saveBitmapError(errMsg: String) {
        SmartToast.show(errMsg)
    }

}
