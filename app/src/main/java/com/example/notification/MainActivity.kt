package com.example.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Color.GREEN
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId="com.example.notification"
    private  val description="Test Notification"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.btn_notify);

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        btn.setOnClickListener {
            val intent = Intent(this, LauncherActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            val contentView = RemoteViews(packageName,R.layout.notification_layout)
            contentView.setTextViewText(R.id.tv_title, "Android")
            contentView.setTextViewText(R.id.tv_content, "Text notification")


            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_0_1)
            {



                notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor= Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)
                builder = Notification.Builder(this,channelId)
                        .setContent(contentView)
                        .setSmallIcon(R.drawable.ic_launcher_round)
                        .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_launcher_round))
                        .setContentIntent(pendingIntent)

            }
            else
            {
                builder = Notification.Builder(this)
                        .setContent(contentView)
                        .setSmallIcon(R.drawable.ic_launcher_round)
                        .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_launcher_round))
                        .setContentIntent(pendingIntent)
            }
            notificationManager.notify(1234,builder.build())


        }
    }
}