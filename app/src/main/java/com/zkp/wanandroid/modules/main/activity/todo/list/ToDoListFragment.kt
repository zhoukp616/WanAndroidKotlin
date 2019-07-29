package com.zkp.wanandroid.modules.main.activity.todo.list

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import butterknife.BindView
import com.coder.zzq.smartshow.toast.SmartToast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zkp.wanandroid.R
import com.zkp.wanandroid.base.fragment.BaseFragment
import com.zkp.wanandroid.bean.ToDo
import com.zkp.wanandroid.modules.main.activity.todo.RefreshTodoEvent
import com.zkp.wanandroid.modules.main.activity.todo.ToDoActivity
import com.zkp.wanandroid.modules.main.activity.todo.adapter.ToDoListAdapter
import com.zkp.wanandroid.modules.main.activity.todo.add.AddToDoActivity
import com.zkp.wanandroid.widget.RecyclerViewImplementsContextMenu
import me.yokeyword.fragmentation.ISupportFragment
import org.simple.eventbus.EventBus

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.todo.list
 * @time: 2019/6/3 11:33
 * @description:
 */
class ToDoListFragment : BaseFragment<ToDoListContract.View, ToDoListContract.Presenter>(), ToDoListContract.View {

    companion object {
        val UPDATE: Int = 0x0001
    }

    @BindView(R.id.refreshLayout)
    lateinit var mRefreshLayout: SmartRefreshLayout

    @BindView(R.id.recyclerView)
    lateinit var mRecyclerView: RecyclerViewImplementsContextMenu

    private var type = 0
    private var status = 0

    private lateinit var mAdapter: ToDoListAdapter

    fun newInstance(bundle: Bundle): ToDoListFragment {
        val fragment = ToDoListFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun createPresenter(): ToDoListContract.Presenter = ToDoListPresenter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_todo_list
    }

    override fun initView() {
        mRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.setHasFixedSize(true)
        registerForContextMenu(mRecyclerView)

        val todoList = mutableListOf<ToDo>()
        mAdapter = ToDoListAdapter(R.layout.item_todo_list, todoList)
        mRecyclerView.adapter = mAdapter
    }

    override fun initEventAndData() {
        type = arguments?.getInt("todo_type")!!
        //使用最新状态
        status = ToDoActivity.mTodoStatus

        initRefreshLayout()

        mPresenter?.refresh(type, status)

        mAdapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(activity, AddToDoActivity::class.java)
            intent.putExtra("from_todo", 1)
            intent.putExtra("item_todo", mAdapter.getItem(position))
            startActivityForResult(intent, UPDATE)
        }

        mAdapter.setOnItemLongClickListener { _, _, _ -> false }

    }

    private fun initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener { refreshLayout ->
            status = ToDoActivity.mTodoStatus
            mPresenter?.refresh(type, status)
            refreshLayout.finishRefresh()
        }

        mRefreshLayout.setOnLoadMoreListener { refreshLayout ->
            mPresenter?.loadMore()
            refreshLayout.finishLoadMore()
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val status = mAdapter.getItem(info.position)?.status
        if (status == 0) {
            menu.add(0, 0, 0, "标记为完成")
        } else {
            menu.add(0, 0, 0, "标记未完成")
        }
        menu.add(0, 1, 0, "删除")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        when (item.itemId) {
            0 -> {
                val status = mAdapter.getItem(menuInfo.position)?.status
                if (status == 0) {
                    mPresenter?.updateToDoStatus(mAdapter.getItem(menuInfo.position)?.id!!, 1)
                } else {
                    mPresenter?.updateToDoStatus(mAdapter.getItem(menuInfo.position)?.id!!, 0)
                }
                return true
            }
            1 -> {
                mPresenter?.deleteToDo(mAdapter.getItem(menuInfo.position)?.id!!)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ISupportFragment.RESULT_OK) {
            when (requestCode) {
                UPDATE -> {
                    status = ToDoActivity.mTodoStatus
                    mPresenter?.refresh(type, status)
                }
            }
        }
    }

    override fun getToDoListSuccess(todoList: MutableList<ToDo>, isFresh: Boolean) {
        if (isFresh) {
            mAdapter.replaceData(todoList)
        } else {
            mAdapter.addData(todoList)
        }
    }

    /**
     * BottomNavigationView的tab点击后调用这个方法，刷新页面
     *
     * @param isVisibleToUser
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            status = ToDoActivity.mTodoStatus
            mPresenter?.refresh(type, status)
        }
    }

    override fun getToDoListError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun deleteToDoSuccess() {
        SmartToast.show("删除成功")
        EventBus.getDefault().post(RefreshTodoEvent(-1))
        status = ToDoActivity.mTodoStatus
        mPresenter?.refresh(type, status)
    }

    fun refresh() {
        status = ToDoActivity.mTodoStatus
        mPresenter?.refresh(type, status)
    }

    override fun deleteToDoError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun updateToDoStatusSuccess() {
        SmartToast.show("更新状态成功")
        EventBus.getDefault().post(RefreshTodoEvent(-1))
        status = ToDoActivity.mTodoStatus
        mPresenter?.refresh(type, status)
    }

    override fun updateToDoStatusError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

}