package com.zkp.android.modules.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import butterknife.BindView
import com.coder.zzq.smartshow.toast.SmartToast
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import com.zkp.android.R
import com.zkp.android.base.activity.BaseActivity
import com.zkp.android.http.AppConfig
import com.zkp.android.modules.home.HomeFragment
import com.zkp.android.modules.knowledge.KnowledgeFragment
import com.zkp.android.modules.login.LoginActivity
import com.zkp.android.modules.navigation.NavigationFragment
import com.zkp.android.modules.project.ProjectFragment
import com.zkp.android.modules.wechat.WeChatFragment

class MainActivity : BaseActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    companion object {
        private const val LOGIN = 0x00001
    }

    @BindView(R.id.toolbar)
    lateinit var mToolBar: Toolbar

    @BindView(R.id.toolbar_title)
    lateinit var mTitle: TextView

    @BindView(R.id.bottomNavigation)
    lateinit var mBottomNavigationView: BottomNavigationView

    @BindView(R.id.navigation)
    lateinit var mNavigation: NavigationView

    @BindView(R.id.faBtn)
    lateinit var mFaBtn: FloatingActionButton

    @BindView(R.id.drawerLayout)
    lateinit var mDrawerLayout: DrawerLayout

    private lateinit var mUsTv: TextView

    /**
     * 当前Fragment的索引
     */
    private var mCurrentFgIndex = 0
    /**
     * 上一个Fragment的索引
     */
    private var mLastFgIndex = -1

    private var mHomeFragment: HomeFragment? = null
    private var mKnowledgeFragment: KnowledgeFragment? = null
    private var mWeChatFragment: WeChatFragment? = null
    private var mNavigationFragment: NavigationFragment? = null
    private var mProjectFragment: ProjectFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mCurrentFgIndex = savedInstanceState.getInt(AppConfig().CURRENT_FRAGMENT_INDEX)
        }
        super.onCreate(savedInstanceState)

        AndPermission.with(this)
            .runtime()
            .permission(*Permission.Group.STORAGE)
            .onGranted { }
            .onDenied {
                AndPermission.with(this)
                    .runtime()
                    .permission(*Permission.Group.STORAGE)
                    .start()
            }
            .start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        var intent: Intent
        when (item?.itemId) {
            R.id.action_usage -> {
                SmartToast.show("常用网站")
            }
            R.id.action_search -> {
                SmartToast.show("搜索")
            }
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(AppConfig().CURRENT_FRAGMENT_INDEX, mCurrentFgIndex)
    }

    override fun initEventAndData() {
        initBottomNavigationView()
        initNavigationView()
        mFaBtn.setOnClickListener {
            //跳转到页面顶部
            jumpToTop()
        }
    }

    private fun initBottomNavigationView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> showFragment(AppConfig().TYPE_HOME_PAGER)
                R.id.navigation_knowledge_hierarchy -> showFragment(AppConfig().TYPE_KNOWLEDGE)
                R.id.navigation_wx_article -> showFragment(AppConfig().TYPE_WX_ARTICLE)
                R.id.navigation_navigation -> showFragment(AppConfig().TYPE_NAVIGATION)
                R.id.navigation_project -> showFragment(AppConfig().TYPE_PROJECT)
            }
            true
        }
    }

    private fun initNavigationView() {
//        mNavigation.setNavigationItemSelectedListener { menuItem ->
//            when(menuItem.itemId){
//                R.id.nav_item_my_collect->
//            }
//            true
//        }
        mUsTv = mNavigation.getHeaderView(0).findViewById(R.id.nav_header_login)
        mUsTv.text = if (mPresenter?.getLoginStatus()!!) mPresenter?.getUserAccount() else getString(R.string.login_in)
        if (!mPresenter?.getLoginStatus()!!) {
            //还没有登录，跳转到登录页面
            mUsTv.setOnClickListener {
                startActivityForResult(
                    Intent(this@MainActivity, LoginActivity::class.java),
                    LOGIN
                )
            }
        }
    }

    /**
     * 跳转到页面顶部
     */
    private fun jumpToTop() {
        when (mCurrentFgIndex) {
            AppConfig().TYPE_HOME_PAGER -> mHomeFragment?.jumpToTop()
            AppConfig().TYPE_KNOWLEDGE -> mKnowledgeFragment?.jumpToTop()
            AppConfig().TYPE_WX_ARTICLE -> mWeChatFragment?.jumpToTop()
            AppConfig().TYPE_NAVIGATION -> mNavigationFragment?.jumpToTop()
            AppConfig().TYPE_PROJECT -> mProjectFragment?.jumpToTop()
        }
    }

    override fun initView() {
        initDrawerLayout()
        showFragment(mCurrentFgIndex)
    }

    private fun showFragment(index: Int) {
        mCurrentFgIndex = index
        val transaction = supportFragmentManager.beginTransaction()
        hideFragment(transaction)
        mLastFgIndex = index
        when (index) {
            AppConfig().TYPE_HOME_PAGER -> {
                mTitle.text = getString(R.string.title_home)
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment().getInstance()
                    transaction.add(R.id.frameLayout, mHomeFragment!!)
                }
                transaction.show(mHomeFragment!!)
            }
            AppConfig().TYPE_KNOWLEDGE -> {
                mTitle.text = getString(R.string.title_knowledge_hierarchy)
                if (mKnowledgeFragment == null) {
                    mKnowledgeFragment = KnowledgeFragment().getInstance()
                    transaction.add(R.id.frameLayout, mKnowledgeFragment!!)
                }
                transaction.show(mKnowledgeFragment!!)
            }
            AppConfig().TYPE_WX_ARTICLE -> {
                mTitle.text = getString(R.string.title_wx_article)
                if (mWeChatFragment == null) {
                    mWeChatFragment = WeChatFragment().getInstance()
                    transaction.add(R.id.frameLayout, mWeChatFragment!!)
                }
                transaction.show(mWeChatFragment!!)
            }
            AppConfig().TYPE_NAVIGATION -> {
                mTitle.text = getString(R.string.title_navigation)
                if (mNavigationFragment == null) {
                    mNavigationFragment = NavigationFragment().getInstance()
                    transaction.add(R.id.frameLayout, mNavigationFragment!!)
                }
                transaction.show(mNavigationFragment!!)
            }
            AppConfig().TYPE_PROJECT -> {
                mTitle.text = getString(R.string.title_project)
                if (mProjectFragment == null) {
                    mProjectFragment = ProjectFragment().getInstance()
                    transaction.add(R.id.frameLayout, mProjectFragment!!)
                }
                transaction.show(mProjectFragment!!)
            }
        }
        transaction.commit()
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        when (mLastFgIndex) {
            AppConfig().TYPE_HOME_PAGER -> mHomeFragment?.let { transaction.hide(it) }
            AppConfig().TYPE_KNOWLEDGE -> mKnowledgeFragment?.let { transaction.hide(it) }
            AppConfig().TYPE_WX_ARTICLE -> mWeChatFragment?.let { transaction.hide(it) }
            AppConfig().TYPE_NAVIGATION -> mNavigationFragment?.let { transaction.hide(it) }
            AppConfig().TYPE_PROJECT -> mProjectFragment?.let { transaction.hide(it) }
        }
    }

    private fun initDrawerLayout() {
        val drawerToggle = ActionBarDrawerToggle(
            this,
            mDrawerLayout,
            mToolBar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerToggle.syncState()
        mDrawerLayout.addDrawerListener(drawerToggle)
    }

    override fun initToolbar() {
        setSupportActionBar(mToolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mTitle.setText(R.string.title_home)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                LOGIN -> {
                    mUsTv.text =
                        if (mPresenter?.getLoginStatus()!!) mPresenter?.getUserAccount() else getString(R.string.login_in)
                    if (mPresenter?.getLoginStatus()!!) {
                        mUsTv.isEnabled = false
                    }
                }
            }
        }
    }
}
