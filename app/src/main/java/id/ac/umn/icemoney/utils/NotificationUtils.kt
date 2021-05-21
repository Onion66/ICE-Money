package id.ac.umn.icemoney.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import id.ac.umn.icemoney.LoginActivity
import id.ac.umn.icemoney.R
import id.ac.umn.icemoney.view.home.HomeFragment

object NotificationUtils {

    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101
    private val money = 0
//    private val timeNotif = 1

    private fun moneyStatus(money: Long): String {
        var textStatus = ""
        if(money < 0){
            textStatus = "Boros"
        }else if(money >= 0){
            textStatus = "Profit"
        }

        return textStatus
    }

//    private fun timeStatus(status: Int): String {
//        var textStatus = ""
//        if(status == 1){
//            textStatus = "Days"
//        }else if(status == 2){
//            textStatus = "Weekly"
//        }else if(status == 3){
//            textStatus = "Monthly"
//        }
//
//        return textStatus
//    }

    //notification
    fun createNotificationChannel(notificationManager: NotificationManager){
        //if statement ini digunakan untuk versi android terbaru (26 keatas / Oreo)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "ICE-Money"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT //bisa diubah ke Hgh / Low tergantung maunya apa
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendNotification(money: Long, intent: Intent, context: Context){
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_ice_money_square)
            .setContentTitle("Your Expense: Rp${money.toInt()}")
            .setContentText("Money Status: [${moneyStatus(money)}]")
//            .setSubText("${timeStatus(timeNotif)} notification")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }
}