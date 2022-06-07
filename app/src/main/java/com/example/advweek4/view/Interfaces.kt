package com.example.advweek4.view

import android.view.View

interface StudentDetailClickListener{
    fun onStudentDetailClick(view: View)
}

interface StudentUpdateClickListener{
    fun onStudentUpdateClick(view: View)
}

interface NotificationClickListener{
    fun onNotificationClick(view: View, name:String)
}

interface TestClickListener{
    fun onTestClick(view: View)
}