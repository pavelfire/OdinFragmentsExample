package com.vk.directop.odinfragmentsexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val LAST_SELECTED_ITEM = "item"

class MainActivity : AppCompatActivity() {

    private lateinit var bottomMenu : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomMenu = findViewById(R.id.bottomNavigationView)

        bottomMenu.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu -> {
                    val menuFragment = MenuFragment()
                    replaceFragment(menuFragment)
//                    supportFragmentManager // обращаемся к fm
//                        .beginTransaction() //начать транзакцию
//                        .add(R.id.fragment_container, menuFragment) // действие
//                        .commit() // закончить транзакцию
                }
                R.id.saveRestore -> {
                    val saveRestoreFragment = SaveRestoreFragment()
                    replaceFragment(saveRestoreFragment)
                }
                R.id.myGraph -> {
                    replaceFragment(SaveRestoreFragment())
                }
                R.id.about -> {
                    val aboutFragment = AboutFragment()
                    replaceFragment(aboutFragment)
//                    supportFragmentManager // обращаемся к fm
//                        .beginTransaction() //начать транзакцию
//                        .add(R.id.fragment_container, aboutFragment) // действие
//                        .commit() // закончить транзакцию
                }
            }
            true
        }

        val menuFragment = MenuFragment()
        supportFragmentManager // обращаемся к fm
            .beginTransaction() //начать транзакцию
            .add(R.id.fragment_container, menuFragment) // действие
            .commit() // закончить транзакцию

        // восстанавливаем выбранный пункт меню при пересоздании активити
        bottomMenu.selectedItemId = savedInstanceState?.getInt(LAST_SELECTED_ITEM) ?: R.id.menu
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager // обращаемся к fm
            .beginTransaction() //начать транзакцию
            .replace(R.id.fragment_container, fragment) // действие
            .commit() // закончить транзакцию
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(LAST_SELECTED_ITEM, bottomMenu.selectedItemId)
        super.onSaveInstanceState(outState)
    }
}


//      val fragment = MyFragment()
//        supportFragmentManager // обращаемся к fm
//            .beginTransaction() //начать транзакцию
//            .add(R.id.fragment_container, fragment) // действие
//            .commit() // закончить транзакцию