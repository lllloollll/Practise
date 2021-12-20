package com.example.pjava.test_kotlin.test_by

import kotlin.reflect.KProperty

/**
 *  @ProjectName:   Practise
 *      @Package:   com.example.pjava.test_kotlin.test_by
 *    @ClassName:   Delegate
 *  @Description:   java类作用描述
 *       @Author:   毛毛虫
 *   @CreateDate:   2021/12/17 5:53 下午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2021/12/17 5:53 下午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
class Delegate {
    var formatString = ""

    operator fun getValue(thisRef: Any, property: KProperty<*>): String =
        formatString + '_' + formatString.length

    operator fun setValue(thisRef: Any, property: KProperty<*>, s: String) {
        if (thisRef is Person2)
            thisRef.updateCount++
        formatString = s.lowercase().replaceFirstChar { it.uppercase() }
    }
}