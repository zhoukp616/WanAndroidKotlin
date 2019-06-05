package com.zkp.android.modules.main.activity.component

import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.widget.TextView
import butterknife.BindView
import com.zkp.android.R
import com.zkp.android.base.activity.BaseActivity
import com.zkp.android.http.AppConfig
import com.zkp.android.modules.main.Fragment.about.AboutUsFragment
import com.zkp.android.modules.main.Fragment.collect.CollectFragment
import com.zkp.android.modules.main.Fragment.search.SearchFragment
import com.zkp.android.modules.main.Fragment.setting.SettingFragment
import com.zkp.android.modules.main.Fragment.usage.UseageFragment
import com.zkp.android.modules.main.Fragment.welfare.WelfareFragment

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity
 * @time: 2019/5/31 11:39
 * @description:
 */
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ComponentActivity : BaseActivity<ComponetContract.View, ComponetContract.Presenter>(),
    ComponetContract.View {

    @BindView(R.id.toolbar_title)
    lateinit var mTitle: TextView

    @BindView(R.id.toolbar)
    lateinit var mToolbar: Toolbar

    lateinit var mTargetFragment: Fragment
    lateinit var title: String

    override fun createPresenter(): ComponetContract.Presenter =
        ComponetPresenter()

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
        val extras = intent.extras
        when (fragType) {
            AppConfig().TYPE_COLLECT -> {
                mTargetFragment = CollectFragment()
                title = getString(R.string.collect)
            }
            AppConfig().TYPE_WELFARE -> {
                mTargetFragment = WelfareFragment()
                title = getString(R.string.nav_welfare)
            }
            AppConfig().TYPE_SETTING -> {
                mTargetFragment = SettingFragment()
                title = getString(R.string.setting)
            }
            AppConfig().TYPE_ABOUT_US -> {
                mTargetFragment = AboutUsFragment()
                title = getString(R.string.about_us)
            }
            AppConfig().TYPE_USEFUL_SITES -> {
                mTargetFragment = UseageFragment()
                title = getString(R.string.useful_sites)
            }
            AppConfig().TYPE_SEARCH_RESULT -> {
                title = extras.getString("search_key", "")
                mTargetFragment = SearchFragment().newInstance(extras!!)
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