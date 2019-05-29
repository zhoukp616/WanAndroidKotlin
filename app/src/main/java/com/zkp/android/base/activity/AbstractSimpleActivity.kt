package com.zkp.android.base.activity

import android.os.Bundle
import butterknife.ButterKnife
import butterknife.Unbinder
import me.yokeyword.fragmentation.SupportActivity

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.base
 * @time: 2019/5/27 14:30
 * @description:
 */
abstract class AbstractSimpleActivity : SupportActivity() {

    private lateinit var mUnBinder: Unbinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mUnBinder = ButterKnife.bind(this)
        onViewCreated()
        initToolbar()
        initView()
        initEventAndData()
    }

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 在initEventAndData()之前执行
     */
    abstract fun onViewCreated()

    /**
     * 初始化toolBar
     */
    abstract fun initToolbar()

    /**
     * 初始化View
     */
    abstract fun initView()

    /**
     * 初始化事件和数据
     */
    abstract fun initEventAndData()

    override fun onDestroy() {
        super.onDestroy()
        if (mUnBinder !== Unbinder.EMPTY) {
            mUnBinder.unbind()
        }
    }

    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            //fragment逐个出栈
            supportFragmentManager.popBackStack()
        }
    }

}