package com.example.test_nsfw

import androidx.annotation.IntegerRes

/**
 *  @ProjectName:   Practise
 *      @Package:   com.example.test_nsfw
 *    @ClassName:   nsfwModel
 *  @Description:   java类作用描述
 *       @Author:   毛毛虫
 *   @CreateDate:   2022/1/18 5:41 下午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2022/1/18 5:41 下午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
data class NsfwModel(@IntegerRes val path: Int, val nsfw: Float?, val sfw: Float?)
