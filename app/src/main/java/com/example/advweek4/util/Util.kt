package com.example.advweek4.util

import android.app.Notification
import android.os.Build
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.example.advweek4.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

@BindingAdapter("android:imageUrl", "android:progressBar")
fun loadImageFromUrl(view: ImageView, url: String?, pb: ProgressBar){
    view.loadImage(url, pb)
}

fun ImageView.loadImage(url: String?, progressBar: ProgressBar){
    Picasso.get()
        .load(url)
        .resize(400,400)
        .centerCrop()
        .error(R.drawable.ic_baseline_error_24)
        .into(this, object : Callback{
            override fun onSuccess() {
                progressBar.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
            }

        })
}

fun createNotificationChannel(contex: Context, importance: Int, showBadge: Boolean, name: String, description:String){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val channelID = "${contex.packageName}--$name"
        val channel = NotificationChannel(channelID, name, importance).apply {
            this.description = description
            setShowBadge(showBadge)
        }
        val notificationManager = contex.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}