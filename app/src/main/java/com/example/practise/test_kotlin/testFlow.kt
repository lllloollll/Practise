package com.example.practise.test_kotlin

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

/**
 *  @ProjectName:   Practise
 *      @Package:   com.example.practise.test_kotlin
 *    @ClassName:   testFlow
 *  @Description:   java类作用描述
 *       @Author:   毛毛虫
 *   @CreateDate:   2022/6/15 3:31 下午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2022/6/15 3:31 下午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
object testFlow {

    suspend fun simple1() = sequence {
        for (i in 1..3) {
            Thread.sleep(100)
            yield(i)
        }
    }

//    suspend fun simple(): List<Int> {
//        delay(1000)
//        return listOf(1, 2, 3)
//    }

    //返回一个流，不需要挂起
    fun simple3(): Flow<Int> = flow {   //流构建器
        for (i in 1..4) {
            "Emitting $i".println()
            delay(100)
            emit(i) //发射值
        }
    }

    suspend fun performRequest(request: Int): String {
        delay(1000)
        return "response $request"
    }

    fun simple4(): Flow<Int> = flow {
        for (i in 1..4) {
            delay(100)
            emit(i)
        }
    }

    fun requestFlow(i: Int): Flow<String> = flow {
        emit("$i : First")
        delay(500)
        emit("$i : Second")
    }

    fun simple():Flow<Int> = flow {
        for (i in 0..5){
            emit(i)
        }
    }

}

fun main() = runBlocking<Unit> {
//    testFlow.simple().forEach { values -> "value:$values".println() }
//    launch {
//        for (k in 1..4) {
//            "I'am not blocked $k".println()
//            delay(100)
//        }
//    }
//
//    val simple = testFlow.simple3()
//    delay(100)
//    simple.collect {    //收集值
//            value ->
//        " $value".println()
//    }
//    delay(500)
//    simple.collect { value -> "collect twice:$value".println() }

//    withTimeoutOrNull(250){
//        testFlow.simple3().collect { value -> " $value".println() }
//    }
//    "Done".println()

//    (1..4).asFlow().collect { value -> " $value".println() }
//    arrayOf(1.44,5.56,3.14159).asFlow().collect { value -> " $value".println() }
//    arrayListOf("apple","pear","banner","watermelon").asFlow().collect { value -> " $value".println() }
//    flowOf("apple", "pear", "banner", "watermelon").collect { value -> println(value) }

//    println("outer:${ Thread.currentThread() }")
//    val value = (1..5).asFlow() //将整数区间转化为流
//        .filter { request ->
//            println("filter:${ Thread.currentThread() }")
//
//            request % 2 != 0 } //过滤出奇数
//        .flowOn(Dispatchers.Default)
//        .map { request -> testFlow.performRequest(request) }    //映射出结果
//        .transform { response ->
//            emit(response)
//            emit(response)
//            emit("test tranform $response")
//        }
//        .reduce { accumulator, value ->
//            println("reduce:${ Thread.currentThread() }")
//
//            accumulator + value }
//
//    "first:$value".println()
//        .collect { response -> println(response) }  //收集流

//   val singleResult= flowOf("test single").single()
//    "single result:$singleResult".println()

    //创建流
//    val flow = (1..10).asFlow()
//    //计算10的阶乘
//    val reduce_result = flow.reduce { accumulator, value -> accumulator * value }
//    //带上初始值0
//    val flod_result = flow.fold(0) { acc, value -> acc * value }
//    "reduce_result:$reduce_result".println()
//    "flod_result:$flod_result".println()

//    val value = (1..10).asFlow().take(5).first()
//    println("value:$value")

//    (1..5).asFlow() //将整数区间转化为流
//        .filter { request -> request % 2 != 0 } //过滤出奇数
//        .map { request -> testFlow.performRequest(request) }  //映射出结果
//        .transform { response ->    //transform 发送两次值
//            emit(response)
//            emit("test transform $response")
//        }
//        .collect { response -> println(response) } //收集流

    //缓冲 buffer
//    val simple=testFlow.simple4()
//    val timeWithoutBuffer= measureTimeMillis {
//        simple.collect { value ->
//            delay(300)
//            println(value)
//        }
//    }
//    println("Collected in $timeWithoutBuffer ms")
//
//    val timeWithBuffer= measureTimeMillis {
//       simple.buffer().collect { value ->
//           delay(300)
//           println(value) }
//    }
//    println("Collected in $timeWithBuffer ms")

    //conflat 合并
//    val simple = testFlow.simple4()
//    simple.conflate()
//        .onEach { delay(600) }
//        .collect { value ->
////            delay(300)
//            println(value)
//        }

    //collectLatest
//    val time = measureTimeMillis {
//        simple
////            .onEach { delay(200) }
//            .collectLatest { value ->
//                println("Collecting $value")
//                delay(300)
//                println("Done $value")
//            }
//    }
//    println("Collected in $time ms")

//    组合多个流
//    val number=(1..3).asFlow()
//    val fruit= arrayListOf("apple","bananar","watermelon","pear").asFlow()
//    number.zip(fruit){a,b->"$a -> $b"}
//        .collect { println("zip:$it") }
//    number.combine(fruit){a,b->"$a->$b"}
//        .collect { println("combine:$it") }


//    val number=(1..3).asFlow().onEach { delay(300) }
//    val fruit= arrayListOf("apple","bananar","watermelon","pear").asFlow().onEach { delay(400) }
//    var startTime=System.currentTimeMillis()
//    //zip
//    number.zip(fruit){a,b->"$a->$b"}
//        .collect { println("$it at ${System.currentTimeMillis()-startTime}ms from zip start") }
//    startTime=System.currentTimeMillis()
//    //combine
//    number.combine(fruit){a,b->"$a->$b"}
//        .collect { println("$it at ${System.currentTimeMillis()-startTime}ms from combine start") }

    //展平流
//    (1..3).asFlow().map { testFlow.requestFlow(it) }
//        .collect { println("map:$it") }
//    (1..3).asFlow().flatMapConcat { testFlow.requestFlow(it) }
//        .collect { println("flatMapConcat:$it") }
//    (1..3).asFlow().flatMapMerge { testFlow.requestFlow(it) }
//        .collect { println("flatMapMerge:$it") }
//    (1..3).asFlow().flatMapLatest { testFlow.requestFlow(it) }
//        .collect { println("flatMapLatest:$it") }

    //流异常
//    try {
//        (1..5).asFlow().collect { value ->
//            println(value)
//            check(value < 3) {  //当value>=3抛出异常
//                "Collected $value"
//            }
//        }
//    } catch (e: Throwable) {
//        println("Caught $e")
//    }

//    (1..5).asFlow()
//        .onEach { value ->
//            check(value < 3) {  //当value>=3抛出异常
//                "Collected $value"
//            }
//            println(value)
//        }
//        .catch { cause -> cause?.let { println(it) } } //对异常进行处理
//        .collect()

    //流完成
//    try {
//        (1..3).asFlow()
//            .collect { println(it) }
//    }finally {
//        println("Done")
//    }

//    (1..5).asFlow()
//        .onCompletion { cause -> cause?.let { println("error:$it") } ?: let { println("Done") } }
//        .collect {
//            check(it < 3) { "Collect value" }
//            println(it)
//        }

    //流的取消
//    testFlow.simple().collect {
//        if (it==3) cancel()
//        println(it)
//    }
    flowOf(1,3,4,5,5)
        .cancellable()
        .collect {
            if (it==3) cancel()
            println(it) }

}

fun String.println() = this.apply {
    System.out.println(this)
}