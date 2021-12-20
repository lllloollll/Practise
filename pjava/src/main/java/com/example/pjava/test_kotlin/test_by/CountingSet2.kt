package com.example.pjava.test_kotlin.test_by

/**
 *  @ProjectName:   Practise
 *      @Package:   com.example.pjava.test_kotlin.test_by
 *    @ClassName:   CountingSet2
 *  @Description:   java类作用描述
 *       @Author:   毛毛虫
 *   @CreateDate:   2021/12/17 4:53 下午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2021/12/17 4:53 下午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
class CountingSet2<T>:MutableSet<T> {
    private val innerSet=HashSet<T>()
    var objectAdded=0
    override fun add(element: T): Boolean {
        objectAdded++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectAdded+=elements.size
        return innerSet.addAll(elements)
    }

    override fun clear() {
        innerSet.clear()
    }

    override fun iterator(): MutableIterator<T> {
        return innerSet.iterator()
    }

    override fun remove(element: T): Boolean {
        return innerSet.remove(element)
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        return innerSet.removeAll(elements)
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        return innerSet.retainAll(elements)
    }

    override val size: Int
        get() = innerSet.size

    override fun contains(element: T): Boolean {
        return innerSet.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return innerSet.containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return innerSet.isEmpty()
    }

    companion object{
        @JvmStatic
        fun main(args:Array<String>){
            val cset2=CountingSet2<Int>()
            cset2.add(1)
            cset2.addAll(listOf(1,2,3,3,4,5))
            println("${cset2.objectAdded} is added and ${cset2.size} is remian")
        }
    }
}