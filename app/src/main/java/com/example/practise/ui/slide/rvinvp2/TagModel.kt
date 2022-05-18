package com.example.practise.ui.slide.rvinvp2

/**
 *  @ProjectName:   InsSaver
 *      @Package:   com.instore.videodownloaderforinstagram.inssaver.insapp.model
 *    @ClassName:   HashtagModel
 *  @Description:   java类作用描述
 *       @Author:   毛毛虫
 *   @CreateDate:   2022/2/22 11:05 上午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2022/2/22 11:05 上午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
data class TagModel(
       val number:Int,
       val content:String,
       val sum:Float,
       val unit:Char,
       var checked:Boolean=false
){
    companion object{
      val   TagUnitMap= hashMapOf<Char,Int>().apply {
          put('g',1000000000)
          put('m',1000000)
          put('k',1000)
      }
    }
}