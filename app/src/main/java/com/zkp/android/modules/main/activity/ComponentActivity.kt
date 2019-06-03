package com.zkp.android.modules.main.activity

import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.widget.TextView
import butterknife.BindView
import com.zkp.android.R
import com.zkp.android.base.activity.BaseActivity
import com.zkp.android.http.AppConfig
import com.zkp.android.modules.main.Fragment.collect.CollectFragment
import com.zkp.android.modules.main.Fragment.usage.UseageFragment
import com.zkp.android.modules.main.Fragment.welfare.WelfareFragment

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity
 * @time: 2019/5/31 11:39
 * @description:
 */
class ComponentActivity : BaseActivity<ComponetContract.View, ComponetContract.Presenter>(), ComponetContract.View {

    @BindView(R.id.toolbar_title)
    lateinit var mTitle: TextView

    @BindView(R.id.toolbar)
    lateinit var mToolbar: Toolbar

    lateinit var mTargetFragment: Fragment
    lateinit var title: String

    override fun createPresenter(): ComponetContract.Presenter = ComponetPresenter()

    override fun getLayoutId(): Int {
        return R.layout.activity_componet
    }

    override fun initToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mToolbar.setNavigationOnClickListener { onBackPressedSupport() }
    }

    override fun initView() {
        val fragType = intent.getIntExtra("type_fragment", -1)
//        val extras = intent.extras
        when (fragType) {
            AppConfig().TYPE_COLLECT -> {
                mTargetFragment = CollectFragment()
                title = getString(R.string.collect)
            }
            AppConfig().TYPE_WELFARE -> {
                mTargetFragment = WelfareFragment()
                title = getString(R.string.nav_welfare)
            }
            AppConfig().TYPE_USEFUL_SITES -> {
                mTargetFragment = UseageFragment()
                title = getString(R.string.useful_sites)
            }
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout, mTargetFragment)
            .commit()
        mTitle.text = title
    }

    override fun initEventAndData() {

    }
}