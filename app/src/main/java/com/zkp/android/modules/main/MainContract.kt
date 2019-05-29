package com.zkp.android.modules.main

import com.zkp.android.base.model.IModel
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main
 * @time: 2019/5/28 10:37
 * @description: MainActivity契约类
 */
interface MainContract {

    interface View : IView {

    }

    interface Presenter : IPresenter<View> {

    }

    interface Model : IModel {

    }

}