package com.example.practise.utils

import android.util.Log
import com.example.practise.BuildConfig

/**
 *  @ProjectName:   Practise
 *      @Package:   com.example.practise.utils
 *    @ClassName:   LogUtils
 *  @Description:   java类作用描述
 *       @Author:   毛毛虫
 *   @CreateDate:   2022/1/11 11:42 上午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2022/1/11 11:42 上午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
object LogUtils {
    val DEBUG = BuildConfig.DEBUG

    fun d(tag: String, content: String) {
        if (DEBUG)
            Log.d(tag, content)
    }

    fun v(tag: String, content: String) {
        if (DEBUG)
            Log.v(tag, content)
    }

    fun w(tag: String, content: String) {
        if (DEBUG)
            Log.w(tag, content)
    }

    fun i(tag: String, content: String) {
        if (DEBUG)
            Log.i(tag, content)
    }

    fun e(tag: String, content: String) {
        if (DEBUG)
            Log.e(tag, content)
    }
}