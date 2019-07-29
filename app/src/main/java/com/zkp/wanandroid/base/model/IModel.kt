package com.zkp.wanandroid.base.model

import io.reactivex.disposables.Disposable

/**
 * @author: zkp
 * @project: WanAndroid
 * @package: com.zkp.android.base.model
 * @time: 2019/5/28 9:30
 * @description:
 */
interface IModel {

    fun addDisposable(disposable: Disposable?)

    fun onDetach()

}