package com.example.practise.test_kotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 *  @ProjectName:   Practise
 *      @Package:   com.example.practise.test_kotlin
 *    @ClassName:   testChannel
 *  @Description:   通道Channel，用来在多个协程之间传递消息
 *       @Author:   毛毛虫
 *   @CreateDate:   2022/6/22 12:37 下午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2022/6/22 12:37 下午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
object testChannel {
    /**
     * 小试牛刀
     */
    fun test1() = runBlocking {
        val channel = Channel<Int>()
        launch {
            for (x in 1..5) channel.send(x * x)    //send 非阻塞
            channel.close() //关闭通道
        }
//    repeat(5){ println(channel.receive())}      //receive 非阻塞，通过receive接收通道的值
        for (y in channel) println(y)   //使用for循环从通道中接收元素，直到通道被关闭
        println("Done")
    }

    /**
     * 构建通道生产者
     */
    fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {     //协程通道构建器，生产者
        for (x in 1..5) send(x * x * x)
    }

    fun test2() = runBlocking {
        val squares = produceSquares()
        squares.consumeEach { println(it) } //消费者
        println("Done")
    }

}

fun main() = runBlocking<Unit> {
//   testChannel.test1()
    testChannel.test2()
}