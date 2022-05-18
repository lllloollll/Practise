package com.example.practise.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.IntRange

/**
 *  @ProjectName:   Practise
 *      @Package:   com.example.practise.notification
 *    @ClassName:   NotificationUtils
 *  @Description:   java类作用描述
 *       @Author:   毛毛虫
 *   @CreateDate:   2022/1/10 4:22 下午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2022/1/10 4:22 下午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
class NotificationUtils(val context: Context) {
    //    fun setBuilder(CHANNEL_ID :String,)

    enum class LEVEL {  //优先级，对标NotificationManager.Importance_...
        Unspecified,
        None,
        Min,
        Low,
        Default,
        High,
        Max
    }

    fun getLevel(level: LEVEL): Int {
        var _level: Int = NotificationManager.IMPORTANCE_DEFAULT
        when (level) {
            LEVEL.Unspecified -> _level = NotificationManager.IMPORTANCE_UNSPECIFIED
            LEVEL.None -> _level = NotificationManager.IMPORTANCE_NONE
            LEVEL.Min -> _level = NotificationManager.IMPORTANCE_MIN
            LEVEL.Low -> _level = NotificationManager.IMPORTANCE_LOW
            LEVEL.High -> _level = NotificationManager.IMPORTANCE_HIGH
            LEVEL.Max -> _level = NotificationManager.IMPORTANCE_MAX
        }
        return _level
    }

    fun getLevel(level: Int): Int {
        if ((level >= NotificationManager.IMPORTANCE_NONE && level <= NotificationManager.IMPORTANCE_MAX)
            || level == NotificationManager.IMPORTANCE_UNSPECIFIED
        )
            return level
        else return NotificationManager.IMPORTANCE_DEFAULT
    }

    /**
     * 创建通知渠道
     */
    fun createNotificationChannel(
        channel_id: String,
        channel_name: String,
        channel_desc: String,
        level: Int
    ) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//            val importance=getLevel(level)
            val channel=NotificationChannel(channel_id,channel_name,level).apply {
                description=channel_desc
            }
            val notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotificationChannel(
        channel_id: String,
        channel_name: String,
        channel_desc: String,
        level: LEVEL
    ){
        createNotificationChannel(channel_id,channel_name,channel_desc,getLevel(level))
    }
}