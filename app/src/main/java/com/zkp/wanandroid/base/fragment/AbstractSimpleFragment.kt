package com.zkp.wanandroid.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import me.yokeyword.fragmentation.SupportFragment

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.base.fragment
 * @time: 2019/5/27 15:39
 * @description:
 */
abstract class AbstractSimpleFragment : SupportFragment() {

    private lateinit var mUnBinder: Unbinder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        mUnBinder = ButterKnife.bind(this, view)
        initView()
        return view
    }

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 有些初始化必须在onCreateView中，例如setAdapter,
     * 否则，会弹出 No adapter attached; skipping layout
     */
    protected abstract fun initView()

    override fun onDestroyView() {
        super.onDestroyView()
        if (mUnBinder !== Unbinder.EMPTY) {
            mUnBinder.unbind()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initEventAndData()
    }

    /**
     * 初始化数据
     */
    protected abstract fun initEventAndData()

}