package com.zkp.android.modules.project

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.text.Html
import android.util.SparseArray
import android.widget.LinearLayout
import butterknife.BindView
import com.coder.zzq.smartshow.toast.SmartToast
import com.zkp.android.R
import com.zkp.android.base.fragment.BaseFragment
import com.zkp.android.bean.ProjectTree
import com.zkp.android.modules.project.list.ProjectListFragment
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
 * @package: com.zkp.android.modules.project
 * @time: 2019/5/30 14:47
 * @description:
 */
class ProjectFragment : BaseFragment<ProjectContract.View, ProjectContract.Presenter>(), ProjectContract.View {

    @BindView(R.id.magicIndicator)
    lateinit var mMagicIndicator: MagicIndicator

    @BindView(R.id.viewPager)
    lateinit var mViewPager: ViewPager

    private lateinit var mProjectTreeList: MutableList<ProjectTree>
    private val fragmentSparseArray = SparseArray<ProjectListFragment>()

    fun getInstance(): ProjectFragment {
        return ProjectFragment()
    }

    override fun createPresenter(): ProjectContract.Presenter = ProjectPresenter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_project
    }

    override fun initView() {

    }

    override fun initEventAndData() {
        mPresenter?.getProjectTree()
    }

    override fun getProjectTreeSuccess(projectTreeList: MutableList<ProjectTree>) {
        this.mProjectTreeList = projectTreeList

        initMagicIndicator()

        initViewPager()

    }

    private fun initMagicIndicator() {
        val commonNavigator = CommonNavigator(context)
        commonNavigator.scrollPivotX = 0.35f
        commonNavigator.adapter = object : CommonNavigatorAdapter() {

            override fun getCount(): Int {
                return mProjectTreeList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(DensityUtils().dip2px(context, 15f), 0, DensityUtils().dip2px(context, 15f), 0)
                simplePagerTitleView.layoutParams = params
                simplePagerTitleView.text = mProjectTreeList[index].name.replace("amp;", "")
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
        mViewPager.adapter = object : FragmentStatePagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment? {
                var projectListFragment: ProjectListFragment? = fragmentSparseArray.get(position)
                return if (projectListFragment != null) {
                    projectListFragment
                } else {
                    val bundle = Bundle()
                    bundle.putInt("cid", mProjectTreeList[position].id)
                    projectListFragment = ProjectListFragment().newInstance(bundle)
                    fragmentSparseArray.put(position, projectListFragment)
                    projectListFragment
                }
            }

            override fun getCount(): Int {
                return mProjectTreeList.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return Html.fromHtml(mProjectTreeList[position].name)
            }
        }
    }

    fun jumpToTop(){
        fragmentSparseArray.get(mViewPager.currentItem)?.jumpToTop()
    }

    override fun getProjectTreeError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }
}