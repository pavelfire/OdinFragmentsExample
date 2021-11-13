package com.vk.directop.odinfragmentsexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}


//  val fragment = MyFragment()
//        supportFragmentManager // обращаемся к fm
//            .beginTransaction() //начать транзакцию
//            .add(R.id.fragment_container, fragment) // действие
//            .commit() // закончить транзакцию