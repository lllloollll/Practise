package com.example.practise.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.*
import com.example.practise.MainActivity
import com.example.practise.R
import com.example.practise.base.BaseActivity
import com.example.practise.ui.slide.rvinvp2.TagsParser

class TestNotificationActivity : BaseActivity() {

    val CHANNEL_ID = "TEST_CHANNEL_ID"
    var notificationId = 1001
    val KEY_TEXT_REPLY = "key_text_reply"
    val conversationId = 1011

    lateinit var messageReplyIntent: Intent
    lateinit var notificationManagerCompat: NotificationManagerCompat

    lateinit var tv_reply: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_notification)


        tv_reply = findViewById(R.id.tv_reply)

        notificationManagerCompat = NotificationManagerCompat.from(this)

        NotificationUtils(this).createNotificationChannel(
            CHANNEL_ID,
            "Test Channel",
            "This is a test channel",
            NotificationUtils.LEVEL.High
        )

        //创建响应
        val intent = Intent(this, TestNotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        //创建操作按钮
        val snoozeIntent = Intent(this, TestNotificationReceiver::class.java).apply {
            action = TestNotificationReceiver.ACTION_TEST_01
            putExtra(TestNotificationReceiver.EXTRA_TEST_01, 888)
        }
        val snoozePendingIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0)

        //添加直接回复
        messageReplyIntent = Intent(this, TestNotificationActivity::class.java)
        var replyLabel = "I am the reply,happy new year!"
        var remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
            setLabel(replyLabel)
            build()
        }
        val replyPendingIntent = PendingIntent.getBroadcast(
            this,
            conversationId,
            messageReplyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        var action = NotificationCompat.Action.Builder(
            R.drawable.ic_launcher_foreground,
            "Reply",
            replyPendingIntent
        )
            .addRemoteInput(remoteInput)
            .build()

        //进度条
        val PROGRESS_MAX = 100
        var PROGRESS_CURRENT = 45

        //全屏Intent
        val fullScreenIntent = Intent(this, MainActivity::class.java)
        val fullScreenPendingIntent =
            PendingIntent.getActivity(this, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher) //小图标
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_launcher_foreground
                )
            )
            .setContentTitle("Test Notification")   //通知标题
            .setContentText("Test Notification Content")    //通知内容
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("I am the big text!\nI am the big text!\nI am the big text!\nI am the big text!\nI am the big text!")
            )  //展开内容
            .setPriority(NotificationCompat.PRIORITY_HIGH)   //优先级
            .setContentIntent(pendingIntent)    //点击响应
            .setAutoCancel(true)    //true:用户点按通知后自动移除通知,false:不移除通知。一般配合pendingIntent使用，点按发生响应后移除通知
//            .addAction(R.drawable.ic_launcher_foreground, "Snooze", snoozePendingIntent)
            .addAction(action)
            .setProgress(PROGRESS_MAX, PROGRESS_CURRENT, true)   //进度条,false:确定进度条    true:确定进度条
            .setFullScreenIntent(fullScreenPendingIntent, true)

        //显示通知

//          notificationManagerCompat.notify(notificationId, builder.build())
    }

    override fun onResume() {
        super.onResume()
        getMessagetext(messageReplyIntent)?.let {
            tv_reply.text = it.toString()
            val repliedNotification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Hi,I am maomao chong")
                .build()
//            notificationManagerCompat.notify(notificationId,repliedNotification)

        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_cancel ->
                notificationManagerCompat.cancel(notificationId)  //移除特定的通知

            R.id.bt_big_picturue_1 -> notificationManagerCompat.notify(
                notificationId,
                bigNotification1("我是标题", "我是内容，没有缩略图")
            )

            R.id.bt_big_picturue_2 -> notificationManagerCompat.notify(
                notificationId,
                bigNotification1("我是标题", "我是内容，我有缩略图")
            )

            R.id.bt_big_text -> notificationManagerCompat.notify(
                notificationId,
                bigTextNotificaton(
                    "我是谁",
                    "我在哪儿",
                    "我要到哪去 \n The login process is not controlled by this app as the link is generated by Instagram. This also means that your data will neither be stored in this app nor on our servers"
                )
            )

            R.id.bt_inbox -> notificationManagerCompat.notify(notificationId, inboxNotification())

            R.id.bt_messsage -> notificationManagerCompat.notify(
                notificationId,
                messageNotification()
            )

            R.id.bt_high -> notificationManagerCompat.notify(notificationId, highNotification())

            R.id.bt_start_normal_activity -> notificationManagerCompat.notify(
                notificationId,
                startActivity()
            )

            R.id.bt_group -> notificationManagerCompat.notify(
                notificationId++,
                groupNotification("点赞${count}", "投币${count++}")
            )
        }
    }

    //检索用户输入
    fun getMessagetext(intent: Intent): CharSequence? {
        return RemoteInput.getResultsFromIntent(intent)?.getCharSequence(KEY_TEXT_REPLY)
    }


    /********************************** 创建展开式通知 *******************************/
    //展开才显示大图
    fun bigNotification1(title: String, content: String): Notification {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_diamond)
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_diamond)
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .build()
    }

    //不展开显示缩略图展开显示大图
    fun bigNotification2(title: String, content: String): Notification {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_diamond)
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(title)
            .setContentText(content)
            .setLargeIcon(bitmap)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
            .build()
    }

    //下拉文字通知
    fun bigTextNotificaton(title: String, content: String, bigText: String): Notification {
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_clean_hand)
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(title)
            .setContentText(content)
            .setLargeIcon(bitmap)
            .setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
            .build()
    }

    //收件箱样式
    fun inboxNotification(): Notification {
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_clean_hand)
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle("I am the Inbox style notification")
            .setContentText("I am the Inbox style notification content")
            .setLargeIcon(bitmap)
            .setStyle(
                NotificationCompat.InboxStyle().addLine("求三连").addLine("点赞").addLine("投币")
                    .addLine("收藏")
            )
            .build()
    }

    //消息样式
    fun messageNotification(): Notification {
        val message1 = NotificationCompat.MessagingStyle.Message(
            "一键三连！！！",
            System.currentTimeMillis() - 100,
            Person.Builder().setName("毛毛虫").build()
        )
        val message2 = NotificationCompat.MessagingStyle.Message(
            "下次一定！！！",
            System.currentTimeMillis(),
            Person.Builder().setName("虫毛毛").build()
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setStyle(
                NotificationCompat.MessagingStyle("长按点赞👍").addMessage(message1)
                    .addMessage(message2)
            )
            .build()
    }

    //高优先级消息
    fun highNotification(): Notification {
        val fullScreenIntent = Intent(this, MainActivity::class.java)
        val fullScreenPendingIntent =
            PendingIntent.getActivity(this, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle("高优先级通知的标题")
            .setContentText("高优先级通知的内容")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .build()
    }

    //启动常规Activity
    fun startActivity(): Notification {
        val resultIntent = Intent(this, TestNotificationActivity::class.java)
        val resultPendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle("启动常规Activity")
            .setContentIntent(resultPendingIntent)
            .build()
    }

    //通知组，实践之后也没有归为一个组啊
    val GROUP_KEY_WORK = "com.android.example.work.maomaochong"
    var count = 0
    fun groupNotification(title: String, content: String): Notification {
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_clean_hand)
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(title)
            .setContentText(content)
            .setLargeIcon(bitmap)
            .setGroup(GROUP_KEY_WORK)
            .setGroupSummary(true)
            .build()
    }

    /**
     * 读取通知渠道设置
     * 打开通知渠道设置
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun setting(){
        val notificationChannel=notificationManagerCompat.getNotificationChannel(CHANNEL_ID)
        notificationChannel?.vibrationPattern
    }

}