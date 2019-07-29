package com.zkp.wanandroid.modules.register

import android.content.Intent
import android.support.design.widget.TextInputLayout
import android.text.TextUtils
import android.widget.Button
import butterknife.BindView
import com.coder.zzq.smartshow.toast.SmartToast
import com.zkp.wanandroid.R
import com.zkp.wanandroid.app.App
import com.zkp.wanandroid.base.activity.BaseActivity
import com.zkp.wanandroid.bean.Login

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.register
 * @time: 2019/5/31 10:48
 * @description:
 */
class RegisterActivity : BaseActivity<RegisterContract.View, RegisterContract.Presenter>(), RegisterContract.View {

    @BindView(R.id.etUserName)
    lateinit var mEtUserName: TextInputLayout

    @BindView(R.id.etPassword)
    lateinit var mEtPassword: TextInputLayout

    @BindView(R.id.etRePassword)
    lateinit var mEtRePassword: TextInputLayout

    @BindView(R.id.btnLogin)
    lateinit var mBtnLogin: Button

    override fun createPresenter(): RegisterContract.Presenter = RegisterPresenter()

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initToolbar() {
    }

    override fun initView() {
        App().addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        App.mActivityList.remove(this)
    }

    override fun initEventAndData() {
        mBtnLogin.setOnClickListener {
            if (TextUtils.isEmpty(mEtUserName.editText?.text.toString())) {
                SmartToast.show("用户名不能为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(mEtPassword.editText?.text.toString())) {
                SmartToast.show("密码不能为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(mEtRePassword.editText?.text.toString())) {
                SmartToast.show("请再次输入密码")
                return@setOnClickListener
            }
            if (mEtPassword.editText!!.text.toString() != mEtRePassword.editText!!.text.toString()) {
                SmartToast.show("两次输入的密码不一致")
                return@setOnClickListener
            }
            mPresenter?.register(
                mEtUserName.editText!!.text.toString(),
                mEtPassword.editText!!.text.toString(),
                mEtRePassword.editText!!.text.toString()
            )
        }
    }

    override fun registerSuccess(data: Login) {
        val intent = Intent()
        intent.putExtra("userName", data.username)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun registerError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }
}