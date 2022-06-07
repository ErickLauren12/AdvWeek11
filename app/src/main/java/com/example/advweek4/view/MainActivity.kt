package com.example.advweek4.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.advweek4.R
import com.example.advweek4.util.createNotificationChannel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    companion object{
        private  var instance:MainActivity? = null

        fun showNotification(title:String, content:String, icon:Int){
            val channelID = "${instance?.packageName}--${instance?.getString(R.string.app_name)}"
            instance?.let {
                val notificationBuilder = NotificationCompat.Builder(it.applicationContext,channelID).apply {
                    setSmallIcon(icon)
                    setContentTitle(title)
                    setContentText(content)
                    setStyle(NotificationCompat.BigTextStyle())
                    priority = NotificationCompat.PRIORITY_DEFAULT
                    setAutoCancel(true)
                }
                val notificationManager = NotificationManagerCompat.from(it.applicationContext)
                notificationManager.notify(1001, notificationBuilder.build())
            }
        }
    }
    init {
        instance = this
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel(this, NotificationManagerCompat.IMPORTANCE_DEFAULT, false, getString(R.string.app_name), "App Notification Channel")

        val observable = Observable.just("a stream of data", "hello", "RxJava")

        val observe = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.d("RxJava", "Begin subscribe")
            }

            override fun onNext(t: String) {
                Log.d("RxJava", t)
            }

            override fun onError(e: Throwable) {
                Log.e("Error", e.message.toString())
            }

            override fun onComplete() {
                Log.d("RxJava", "Complete")
            }

        }
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observe)
        Observable.just("Another Stream of data", "Hello Again", "RxKotlin")
            .subscribe(
                { Log.d("rxkotlin", it) },
                { Log.e("rxError", it.message.toString()) },
                { Log.i("rxkotlin", "finish") }
            )
        Observable
            .timer(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("Delay", "Delay by 5 second")
            }
    }
}