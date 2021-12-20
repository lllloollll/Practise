package com.example.pjava.test_kotlin.test_by

/**
 *  @ProjectName:   Practise
 *      @Package:   com.example.pjava.test_kotlin.test_by
 *    @ClassName:   CountingSet3
 *  @Description:   java类作用描述
 *       @Author:   毛毛虫
 *   @CreateDate:   2021/12/17 5:02 下午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2021/12/17 5:02 下午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
class CountingSet3<T>(val innerSet: MutableSet<T> = HashSet<T>()) :
    MutableSet<T> by innerSet {
    var objectAdded = 0
    override fun add(element: T): Boolean {
        objectAdded++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectAdded += elements.size
        return innerSet.addAll(elements)
    }

    companion object{
        @JvmStatic
        fun main(args:Array<String>){
            val cset3=CountingSet3<Int>()
            cset3.add(1)
            cset3.addAll(listOf(1,1,2,3,4,5))
            println("${cset3.objectAdded} objects are added and ${cset3.size} objects are remian")
        }
    }
}