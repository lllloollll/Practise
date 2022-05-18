package com.example.pjava.test_kotlin.test_by

/**
 *  @ProjectName:   Practise
 *      @Package:   com.example.pjava.test_kotlin.test_by
 *    @ClassName:   CheckedMultableList
 *  @Description:   java类作用描述
 *       @Author:   毛毛虫
 *   @CreateDate:   2022/2/22 5:33 下午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2022/2/22 5:33 下午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
class CheckedMultableList<T>(var list: MutableList<T>) :MutableList<T> by list{

    var checked=0
    fun ooo(){
//        list= listOf<T>()
    }

}