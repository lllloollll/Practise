package com.example.pjava.test_kotlin.test_by


/**
 *  @ProjectName:   Practise
 *      @Package:   com.example.pjava.test_kotlin.test_by
 *    @ClassName:   Person2
 *  @Description:   java类作用描述
 *       @Author:   毛毛虫
 *   @CreateDate:   2021/12/17 5:32 下午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2021/12/17 5:32 下午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
class Person2 {
    var updateCount = 0
    var name: String by Delegate()

    var lastname: String by Delegate()



    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val person = Person2()
            person.name = "maomao"
            person.lastname = "chong"
            println("person.name:${person.name}\nperson.lastname:${person.lastname}\nperson.updateCount:${person.updateCount}")
        }
    }

}