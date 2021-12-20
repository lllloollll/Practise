package com.example.pjava.test_kotlin.test_by

/**
 *  @ProjectName:   Practise
 *      @Package:   com.example.pjava.test_kotlin.test_by
 *    @ClassName:   CountingSet1
 *  @Description:   java类作用描述
 *       @Author:   毛毛虫
 *   @CreateDate:   2021/12/17 10:34 上午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2021/12/17 10:34 上午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
class CountingSet1<T> : HashSet<T>() {
    var objectAdded = 0

    override fun add(element: T): Boolean {
        objectAdded++
        return super.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        //调用add(T)
        return super.addAll(elements)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cset = CountingSet1<Int>()
            cset.add(1)
            cset.addAll(listOf(2, 3, 5, 5, 1))
            println("${cset.objectAdded} objects were added,${cset.size} remain")
        }
    }
}