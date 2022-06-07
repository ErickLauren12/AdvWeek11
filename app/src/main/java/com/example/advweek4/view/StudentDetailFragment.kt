package com.example.advweek4.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.advweek4.R
import com.example.advweek4.databinding.FragmentStudentDetailBinding
import com.example.advweek4.model.Student
import com.example.advweek4.util.loadImage
import com.example.advweek4.viewmodel.DetailViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_student_detail.*
import kotlinx.android.synthetic.main.student_list_item.*
import java.util.concurrent.TimeUnit
import kotlin.math.log

class StudentDetailFragment : Fragment(), StudentUpdateClickListener, NotificationClickListener, TestClickListener {
    private lateinit var viewModel: DetailViewModel
    private  lateinit var dataBinding: FragmentStudentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_student_detail, container, false)
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val id = StudentDetailFragmentArgs.fromBundle(requireArguments()).studentId
        viewModel.detail(id)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.studentLiveData.observe(viewLifecycleOwner){
            dataBinding.studentDetail = it
            //imageViewStudent.loadImage(it.photoUrl, progressBarStudentDetail)
            /*val student = it
            student?.let {
                imageViewStudent.loadImage(it.photoUrl,progressStudentDetailPhoto)
                editID.setText(it.id)
                editName.setText(it.name)
                editDOB.setText(it.dob)
                editPhone.setText(it.phone)

                buttonNotification.setOnClickListener {
                    Observable.timer(5, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe{
                            Log.d("My Notification", "Notification Delay")
                            student.name?.let { it1 -> MainActivity.showNotification(it1, "A Notification Created", R.drawable.ic_baseline_person_24) }
                        }
                }
            }*/
        }
        dataBinding.updateListener = this
        dataBinding.notificationListener = this
    }

    override fun onStudentUpdateClick(view: View) {
        Log.d("Update", "Masuk")
        Toast.makeText(view.context, "Data Updated", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(view).popBackStack()
    }

    override fun onNotificationClick(view: View, name:String) {
        Observable.timer(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.d("My Notification", "Notification Delay")
                name?.let { it1 -> MainActivity.showNotification(it1, "A Notification Created", R.drawable.ic_baseline_person_24) }
            }
        Navigation.findNavController(view).popBackStack()
    }

    override fun onTestClick(view: View) {
        Toast.makeText(view.context, "Data Updated", Toast.LENGTH_SHORT).show()
    }

}