package com.zkp.android.modules.main.activity.todo.add

import android.annotation.SuppressLint
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.coder.zzq.smartshow.dialog.ChooseListDialog
import com.coder.zzq.smartshow.dialog.ChooseResult
import com.coder.zzq.smartshow.toast.SmartToast
import com.zkp.android.R
import com.zkp.android.base.activity.BaseActivity
import com.zkp.android.bean.ToDo
import com.zkp.android.http.AppConfig
import com.zkp.android.modules.main.activity.todo.RefreshTodoEvent
import org.simple.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.todo.add
 * @time: 2019/6/3 16:32
 * @description:
 */
class AddToDoActivity : BaseActivity<AddToDoContract.View, AddToDoContract.Presenter>(), AddToDoContract.View {
    @BindView(R.id.toolbar)
    lateinit var mToolbar: Toolbar

    @BindView(R.id.toolbar_title)
    lateinit var mTitle: TextView

    @BindView(R.id.et_add_todo_title)
    lateinit var mAddTodoTitle: EditText

    @BindView(R.id.et_add_todo_content)
    lateinit var mAddTodoContent: EditText

    @BindView(R.id.rg_todo_priority)
    lateinit var mPriorityRg: RadioGroup

    @BindView(R.id.rb_todo_priority_1)
    lateinit var mTodoPriority1: RadioButton

    @BindView(R.id.rb_todo_priority_2)
    lateinit var mTodoPriority2: RadioButton

    @BindView(R.id.tv_add_todo_label_content)
    lateinit var mAddTodoLabel: TextView

    @BindView(R.id.tv_add_todo_date_content)
    lateinit var mAddTodoDate: TextView

    /**
     * 0--增加ToDo  1--更新ToDo
     */
    private var from: Int = 0

    private val types = arrayOf("无标签", "工作", "学习", "生活", "其他")

    private var mTypeChooseDialog: ChooseListDialog? = null
    private var preChoosePositionType = 0
    private lateinit var todoItem: ToDo

    override fun createPresenter(): AddToDoContract.Presenter = AddToDoPresenter()

    override fun getLayoutId(): Int {
        return R.layout.activity_add_todo
    }

    override fun initToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mToolbar.setNavigationOnClickListener { onBackPressedSupport() }
    }

    override fun initView() {

    }

    override fun initEventAndData() {
        from = intent.getIntExtra("from_todo", 0)

        if (from == 0) {
            //增加todo数据
            mTitle.setText(R.string.todo_new_title)
            //获取要添加的type类型
            preChoosePositionType = intent.getIntExtra("type_todo", 0)
        } else {
            //更新todo数据
            mTitle.setText(R.string.todo_edit_title)

            //获取要更新的todo数据
            todoItem = intent.getSerializableExtra("item_todo") as ToDo

            //将要更新的todo数据设置到页面上
            mAddTodoTitle.setText(todoItem.title)
            mAddTodoContent.setText(todoItem.content)

            if (todoItem.priority == 1) {
                mTodoPriority1.isChecked = true
                mTodoPriority2.isChecked = false
            } else {
                mTodoPriority1.isChecked = false
                mTodoPriority2.isChecked = true
            }

            mAddTodoLabel.text = types[todoItem.type]
            mAddTodoDate.text = todoItem.dateStr

            preChoosePositionType = todoItem.type
        }
    }

    @OnClick(
        R.id.tv_add_todo_label_content,
        R.id.iv_label_arrow_right,
        R.id.tv_add_todo_date_content,
        R.id.iv_date_arrow_right,
        R.id.bt_todo_save
    )
    internal fun onClick(view: View) {
        when (view.id) {
            R.id.iv_label_arrow_right, R.id.tv_add_todo_label_content ->
                //选择标签
                showTypeChooseDialog()
            R.id.iv_date_arrow_right, R.id.tv_add_todo_date_content ->
                //选择时间
                showTimePicker()
            R.id.bt_todo_save -> if (from == 0) {
                addToDo()
            } else {
                updateToDo()
            }
            else -> {
            }
        }
    }

    @SuppressLint("RtlHardcoded")
    private fun showTypeChooseDialog() {
        if (mTypeChooseDialog == null) {
            mTypeChooseDialog = ChooseListDialog()
                .title("请选择标签")
                .defaultChoosePos(preChoosePositionType)
                .checkMarkPos(Gravity.LEFT)
                .checkMarkColorRes(R.color.bottom_nav_checked)
                .choiceMode(ChooseListDialog.CHOICE_MODE_SINGLE)
                .keepChosenPosByLast(true)
                .items(types)
                .confirmBtn("确定") { dialog, _, data ->
                    dialog.dismiss()
                    preChoosePositionType = (data as ChooseResult).choosePositions[0]
                    mAddTodoLabel.text = data.chooseItems[0] as String
                }
        }
        mTypeChooseDialog?.showInActivity(this)
    }

    private fun showTimePicker() {

        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()

        //正确设置方式 原因：注意事项有说明
        startDate.set(2019, 1, 1)
        endDate.set(2100, 12, 31)

        val pvTime = TimePickerBuilder(this) { date, _ ->
            //选中事件回调
            mAddTodoDate.text = getTime(date)
        }
            //默认全部显示
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .setCancelText("取消")//取消按钮文字
            .setSubmitText("确定")//确认按钮文字
            .setTitleSize(20)//标题文字大小
            .setTitleText("选择日期")//标题文字
            .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
            .isCyclic(false)//是否循环滚动
            .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
            .setRangDate(startDate, endDate)//起始终止年月日设定
            .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
            .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
            .isDialog(false)//是否显示为对话框样式
            .build()

        pvTime.show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTime(date: Date): String {
        return SimpleDateFormat("yyyy-MM-dd").format(date)
    }

    /**
     * 添加todo数据
     */
    private fun addToDo() {
        val map = HashMap<String, Any>(6)
        map[AppConfig().KEY_TODO_TITLE] = mAddTodoTitle.text.toString()
        map[AppConfig().KEY_TODO_CONTENT] = mAddTodoContent.text.toString()
        map[AppConfig().KEY_TODO_DATE] = mAddTodoDate.text.toString()
        map[AppConfig().KEY_TODO_TYPE] = preChoosePositionType
        map[AppConfig().KEY_TODO_STATUS] = 0
        map[AppConfig().KEY_TODO_PRIORITY] =
            if (mTodoPriority1.isChecked) AppConfig().TODO_PRIORITY_FIRST else AppConfig().TODO_PRIORITY_SECOND
        mPresenter?.addToDo(map)
    }

    /**
     * 更新todo数据
     */
    private fun updateToDo() {
        val map = HashMap<String, Any>(6)
        map[AppConfig().KEY_TODO_TITLE] = mAddTodoTitle.text.toString()
        map[AppConfig().KEY_TODO_CONTENT] = mAddTodoContent.text.toString()
        map[AppConfig().KEY_TODO_DATE] = mAddTodoDate.text.toString()
        map[AppConfig().KEY_TODO_TYPE] = preChoosePositionType
        map[AppConfig().KEY_TODO_STATUS] = todoItem.status
        map[AppConfig().KEY_TODO_PRIORITY] =
            if (mTodoPriority1.isChecked) AppConfig().TODO_PRIORITY_FIRST else AppConfig().TODO_PRIORITY_SECOND
        mPresenter?.updateToDo(todoItem.id, map)
    }

    override fun addToDoSuccess() {
        EventBus.getDefault().post(RefreshTodoEvent(-1))
        SmartToast.show("添加成功")
        finish()
    }

    override fun addToDoError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun updateToDoSuccess() {
        EventBus.getDefault().post(RefreshTodoEvent(-1))
        SmartToast.show("更新成功")
        finish()
    }

    override fun updateToDoError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

}
