package com.zkp.android.modules.main.activity

import com.zkp.android.base.model.IModel
import com.zkp.android.base.presenter.IPresenter
import com.zkp.android.base.view.IView

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.activity
 * @time: 2019/5/31 11:40
 * @description:
 */
class ComponetContract {

    interface View :IView{

    }

    interface Presenter : IPresenter<View>{

    }

    interface Model :IModel{

    }

}