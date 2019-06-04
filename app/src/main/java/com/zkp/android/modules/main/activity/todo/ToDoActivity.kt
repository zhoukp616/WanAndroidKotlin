package com.zkp.android.modules.main.activity.todo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.SparseArray
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import com.zkp.android.R
import com.zkp.android.base.activity.BaseActivity
import com.zkp.android.http.AppConfig
import com.zkp.android.modules.main.activity.todo.add.AddToDoActivity
import com.zkp.android.modules.main.activity.todo.list.ToDoListFragment
import com.zkp.android.utils.DensityUtils
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import org.simple.eventbus.EventBus
import java.util.*

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.todo
 * @time: 2019/6/3 10:51
 * @description:
 */
class ToDoActivity : BaseActivity<ToDoContract.View, ToDoContract.Presenter>(), ToDoContract.View {

    companion object {
        /**
         * todo事项完成状态 0--未完成   1--已完成
         */
        var mTodoStatus: Int = 0
        val ADD = 0x0001
    }

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

    @BindView(R.id.navigation)
    lateinit var mNavigation: BottomNavigationView

    private val mTodoTypeArray = SparseArray<String>(5)
    private val fragmentSparseArray = SparseArray<ToDoListFragment>(5)

    override fun createPresenter(): ToDoContract.Presenter = ToDoPresenter()

    override fun getLayoutId(): Int {
        return R.layout.activity_todo
    }

    override fun initToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mTitle.setText(R.string.todo_title)
        mToolbar.setNavigationOnClickListener { onBackPressedSupport() }
    }

    override fun initView() {
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        mNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_not_todo -> if (mTodoStatus == 1) {
                    mTodoStatus = 0
                    EventBus.getDefault().post(RefreshTodoEvent(0))
                    fragmentSparseArray.get(mViewPager.currentItem).userVisibleHint = true
                }
                R.id.action_todo_done -> if (mTodoStatus == 0) {
                    mTodoStatus = 1
                    EventBus.getDefault().post(RefreshTodoEvent(1))
                    fragmentSparseArray.get(mViewPager.currentItem).userVisibleHint = true
                }
            }
            true
        }
    }

    override fun initEventAndData() {
        initTodoTypeList()
        initMagicIndicator()
        initViewPagerAndTabLayout()

        mFaBtn.setOnClickListener {
            //添加todo事件
            val intent = Intent(this@ToDoActivity, AddToDoActivity::class.java)
            intent.putExtra("from_todo", 0)
            intent.putExtra("type_todo", mViewPager.currentItem)
            startActivityForResult(intent, ADD)
        }
    }

    private fun initTodoTypeList() {
        mTodoTypeArray.put(AppConfig().TODO_TYPE_ALL, getString(R.string.todo_all))
        mTodoTypeArray.put(AppConfig().TODO_TYPE_WORK, getString(R.string.todo_work))
        mTodoTypeArray.put(AppConfig().TODO_TYPE_STUDY, getString(R.string.todo_study))
        mTodoTypeArray.put(AppConfig().TODO_TYPE_LIFE, getString(R.string.todo_life))
        mTodoTypeArray.put(AppConfig().TODO_TYPE_OTHER, getString(R.string.todo_other))
    }

    private fun initMagicIndicator() {
        val commonNavigator = CommonNavigator(this)
        commonNavigator.scrollPivotX = 0.35f
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {

            override fun getCount(): Int {
                return mTodoTypeArray.size()
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(DensityUtils().dip2px(context, 25f), 0, DensityUtils().dip2px(context, 25f), 0)
                simplePagerTitleView.layoutParams = params
                simplePagerTitleView.text = mTodoTypeArray[index]
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

    private fun initViewPagerAndTabLayout() {
        mViewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment? {
                var todoListFragment: ToDoListFragment? = fragmentSparseArray.get(position)
                if (todoListFragment != null) {
                    return todoListFragment
                } else {
                    val bundle = Bundle()
                    bundle.putInt("todo_type", position)
                    todoListFragment = ToDoListFragment().newInstance(bundle)
                    fragmentSparseArray.put(position, todoListFragment)
                    return todoListFragment
                }
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

            }

            override fun getCount(): Int {
                return mTodoTypeArray.size()
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return mTodoTypeArray.get(position)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD -> {
                    fragmentSparseArray[mViewPager.currentItem].refresh()
                }
            }
        }
    }

}