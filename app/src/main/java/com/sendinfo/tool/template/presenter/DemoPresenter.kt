package com.sendinfo.tool.template.presenter

import android.text.TextUtils
import com.sendinfo.tool.entitys.response.BaseResponse
import com.base.library.http.HttpDto
import com.base.library.mvp.BPresenterImpl
import com.base.library.util.tryCatch
import com.sendinfo.tool.template.contract.DemoContract
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import talex.zsw.basecore.util.JsonTool

/**
 * 作用: 使用案例,自己定义Presenter
 * 当前为异步JsonTool解析，requestSuccess中也可同步直接解析
 */
class DemoPresenter(view: DemoContract.View) : BPresenterImpl<DemoContract.View>(view), DemoContract.Presenter {

    override fun requestSuccess(body: String, baseHttpDto: HttpDto) {
        super.requestSuccess(body, baseHttpDto)
        asyncJsonTool<BaseResponse>(body,
            { requestSuccess(it, baseHttpDto) },
            { requestError(it, baseHttpDto) })//调用RxJavaIO异步解析
    }

    private fun requestSuccess(baseResponse: BaseResponse, baseHttpDto: HttpDto) {
        when (baseHttpDto.url) {
            "" -> {
            }
        }
    }

    override fun check(idCard: String) {
        if (TextUtils.isEmpty(idCard)) {
            val bRequest = HttpDto("").apply {
                params = mapOf("key" to "", "cardno" to idCard)
            }
            getData(bRequest)
        } else {
            mView?.loginError("身份证不能少于18位")
        }
    }

}
