package com.example.test_nsfw

import android.app.Application
import io.github.devzwy.nsfw.NSFWHelper

/**
 *  @ProjectName:   Practise
 *      @Package:   com.example.test_nsfw
 *    @ClassName:   NsfwApp
 *  @Description:   java类作用描述
 *       @Author:   毛毛虫
 *   @CreateDate:   2022/1/18 5:02 下午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2022/1/18 5:02 下午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
class NsfwApp: Application() {
    override fun onCreate() {
        super.onCreate()
        //开启日志
        NSFWHelper.openDebugLog()
        //初始化
        NSFWHelper.initHelper(this)
    }
}