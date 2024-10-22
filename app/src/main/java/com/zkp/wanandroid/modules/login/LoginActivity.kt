package com.zkp.wanandroid.modules.login

import android.content.Intent
import android.support.design.widget.TextInputLayout
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import com.coder.zzq.smartshow.toast.SmartToast
import com.zkp.wanandroid.R
import com.zkp.wanandroid.app.App
import com.zkp.wanandroid.base.activity.BaseActivity
import com.zkp.wanandroid.bean.Login
import com.zkp.wanandroid.modules.register.RegisterActivity
import com.zkp.wanandroid.utils.SpUtils

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.login
 * @time: 2019/5/31 10:08
 * @description:
 */
class LoginActivity : BaseActivity<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {

    companion object {
        private const val REGISTER = 0x0001
    }

    @BindView(R.id.etUserName)
    lateinit var mEtUserName: TextInputLayout

    @BindView(R.id.etPassword)
    lateinit var mEtPassword: TextInputLayout

    @BindView(R.id.btnLogin)
    lateinit var mBtnLogin: Button

    @BindView(R.id.tvRegister)
    lateinit var mTvRegister: TextView

    override fun createPresenter(): LoginContract.Presenter = LoginPresenter()

    override fun getLayoutId(): Int {
        return R.layout.activity_login
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
            mPresenter?.login(mEtUserName.editText!!.text.toString(), mEtPassword.editText!!.text.toString())
        }

        mTvRegister.setOnClickListener {
            mEtUserName.editText?.setText("")
            mEtPassword.editText?.setText("")
            startActivityForResult(Intent(this@LoginActivity, RegisterActivity::class.java), REGISTER)
        }
    }

    override fun loginSuccess(data: Login) {
        SpUtils().putBoolean(this, "loginStatus", true)
        mPresenter?.setUserAccount(data.username)
        setResult(RESULT_OK)
        finish()
    }

    override fun loginError(errorMsg: String) {
        SmartToast.show(errorMsg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REGISTER -> {
                    assert(data != null)
                    mEtUserName.editText?.setText(data!!.getStringExtra("userName"))
                }
            }
        }
    }
}