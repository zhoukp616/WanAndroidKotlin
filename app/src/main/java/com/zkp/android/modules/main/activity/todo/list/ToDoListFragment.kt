package com.zkp.android.modules.main.activity.todo.list

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import butterknife.BindView
import com.coder.zzq.smartshow.toast.SmartToast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zkp.android.R
import com.zkp.android.base.fragment.BaseFragment
import com.zkp.android.bean.ToDo
import com.zkp.android.modules.main.activity.todo.ToDoActivity
import com.zkp.android.modules.main.activity.todo.adapter.ToDoListAdapter
import com.zkp.android.modules.main.activity.todo.add.AddToDoActivity
import com.zkp.android.widget.RecyclerViewImplementsContextMenu

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity.todo.list
 * @time: 2019/6/3 11:33
 * @description:
 */
class ToDoListFragment : BaseFragment<ToDoListContract.View, ToDoListContract.Presenter>(), ToDoListContract.View {

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
            startActivity(intent)
        }
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

    override fun getToDoListSuccess(todoList: MutableList<ToDo>, isFresh: Boolean) {
        if (isFresh) {
            mAdapter.replaceData(todoList)
        } else {
            mAdapter.addData(todoList)
        }
    }

    override fun getToDoListError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }
}