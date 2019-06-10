package com.zkp.android.modules.knowledge.detail

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.text.Html
import android.util.SparseArray
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import com.zkp.android.R
import com.zkp.android.app.App
import com.zkp.android.base.activity.BaseActivity
import com.zkp.android.bean.Knowledge
import com.zkp.android.bean.KnowledgeTreeBody
import com.zkp.android.modules.knowledge.list.KnowledgeListFragment
import com.zkp.android.utils.DensityUtils
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import java.util.*

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.knowledge.detail
 * @time: 2019/5/29 15:01
 * @description:
 */
class KnowledgeDetailActivity : BaseActivity<KnowledgeDetailContract.View, KnowledgeDetailContract.Presenter>(),
    KnowledgeDetailContract.View {

    @BindView(R.id.toolbar)
    lateinit var mToolbar: Toolbar

    @BindView(R.id.toolbar_title)
    lateinit var mTitle: TextView

    @BindView(R.id.magicIndicator)
    lateinit var mMagicIndicator: MagicIndicator

    @BindView(R.id.viewPager)
    lateinit var mViewPager: ViewPager

    @BindView(R.id.faBtn)
    lateinit var mFaBtn: FloatingActionButton

    private var mKnowledgeList = mutableListOf<Knowledge>()
    private val fragmentSparseArray = SparseArray<KnowledgeListFragment>()

    override fun createPresenter(): KnowledgeDetailContract.Presenter = KnowledgeDetailPresenter()

    override fun getLayoutId(): Int {
        return R.layout.activity_knowledge_detail
    }

    override fun initToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mToolbar.setNavigationOnClickListener { onBackPressedSupport() }
    }

    override fun initView() {
        App().addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        App.mActivityList.remove(this)
    }

    override fun initEventAndData() {

        intent.extras?.let {
            it.getSerializable("knowledge_item")?.let { it1 ->
                val data = it1 as KnowledgeTreeBody
                data.children.let { children ->
                    mKnowledgeList.addAll(children)
                }
            }
        }

        initMagicIndicator()
        initViewPager()
    }

    private fun initMagicIndicator() {
        val commonNavigator = CommonNavigator(this)
        commonNavigator.scrollPivotX = 0.35f
        commonNavigator.adapter = object : CommonNavigatorAdapter() {

            override fun getCount(): Int {
                return mKnowledgeList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(DensityUtils().dip2px(context, 15f), 0, DensityUtils().dip2px(context, 15f), 0)
                simplePagerTitleView.layoutParams = params
                simplePagerTitleView.text = mKnowledgeList[index].name
                simplePagerTitleView.normalColor =
                    Objects.requireNonNull(context).resources.getColor(R.color.Grey800)
                simplePagerTitleView.selectedColor =
                    Objects.requireNonNull(context).resources.getColor(R.color.colorWhiteTitle)
                simplePagerTitleView.setOnClickListener { mViewPager.currentItem = index }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = WrapPagerIndicator(context)
                indicator.fillColor = Objects.requireNonNull(context).resources.getColor(R.color.colorPrimary)
                return indicator
            }
        }

        mMagicIndicator.navigator = commonNavigator
        ViewPagerHelper.bind(mMagicIndicator, mViewPager)

    }

    private fun initViewPager() {
        mViewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment? {
                var knowledgeListFragment: KnowledgeListFragment? = fragmentSparseArray.get(position)
                return if (knowledgeListFragment != null) {
                    knowledgeListFragment
                } else {
                    val bundle = Bundle()
                    bundle.putInt("cid", mKnowledgeList[position].id)
                    knowledgeListFragment = KnowledgeListFragment().newInstance(bundle)
                    fragmentSparseArray.put(position, knowledgeListFragment)
                    knowledgeListFragment
                }
            }

            override fun getCount(): Int {
                return mKnowledgeList.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return Html.fromHtml(mKnowledgeList[position].name)
            }
        }
    }

    fun jumpToTop() {
        val currentFragment = fragmentSparseArray.get(mViewPager.currentItem)
        currentFragment?.jumpToTop()
    }
}
