package com.android.datastoretest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var userRepository: UserRepository
    var dataName: String = " "
    var dataNum: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userRepository = UserRepository(applicationContext)

        val tv_result = findViewById<TextView>(R.id.tv_result)
        val btn_read = findViewById<Button>(R.id.btn_read)
        btn_read.setOnClickListener {
            GlobalScope.launch {
                dataName = userRepository.readName()!!
                dataNum = userRepository.readNum()!!
                tv_result.setText(dataName + " " + dataNum.toString())
                Log.e("test", dataName + " " + dataNum.toString())
            }
            tv_result.setText(dataNum.toString() + " " + dataName)
        }
        val btn_create = findViewById<Button>(R.id.btn_create)
        btn_create.setOnClickListener {
            GlobalScope.launch {

                userRepository.createStoreData()
            }
        }

//        GlobalScope.launch {
//            //updateShowCompleted(false)
//            userRepository.createStoreData()
//            userRepository.readNum()
//
//        }

        userRepository.flowNum.asLiveData().observe(this, Observer {
            dataNum = it
            tv_result.setText(dataNum.toString() + " " + dataName)
        })

        userRepository.flowName.asLiveData().observe(this, Observer {
            dataName = it
            tv_result.setText(dataNum.toString() + " " + dataName)
        })


    }
}