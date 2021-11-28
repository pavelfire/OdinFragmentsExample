package com.vk.directop.odinfragmentsexample

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson


class SaveRestoreFragment : Fragment() {

    private lateinit var aboutButton: Button
    //lateinit var view : View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_save_restore, container, false)

        val btStore: Button = view.findViewById(R.id.storeButton)
        val btGet: Button = view.findViewById(R.id.getButton)

        btStore.setOnClickListener {
            val inputFirstName =
                view.findViewById<View>(R.id.inputFirstName) as EditText
            val inputLastName =
                view.findViewById<View>(R.id.inputLastName) as EditText
            val inputAge =
                view.findViewById<View>(R.id.inputAge) as EditText
            val obj = Person()
            obj.myValue = 2
            obj.myFirstName = inputFirstName.text.toString()
            obj.myLastName = inputLastName.text.toString()
            obj.myAge = inputAge.text.toString()
            val appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.requireContext())
            val prefsEditor = appSharedPrefs.edit()
            val gson = Gson()
            val json = gson.toJson(obj)
            prefsEditor.putString("MyObject", json)
            prefsEditor.commit()
            Toast.makeText(this.requireContext(), "Object stored in SharedPreferences", Toast.LENGTH_LONG)
        }
        btGet.setOnClickListener {
            val outputEditText =
                view.findViewById<View>(R.id.outputEditText) as TextView
            val appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.requireContext())
            val gson = Gson()
            val json = appSharedPrefs.getString("MyObject", "")
            val obj = gson.fromJson(json, Person::class.java)
            outputEditText.setText("obj.MyFirstName: [" + obj.myFirstName + "] obj.MyLastName: [" + obj.myLastName + "] obj.myAge: [" + obj.myAge + "] obj.MyValue: [" + obj.myValue + "]")

            Toast.makeText(this.requireContext(), "Object fetch from SharedPreferences successfully", Toast.LENGTH_LONG)
        }



        return view
    }

}

//https://handyopinion.com/how-to-save-and-retrieve-class-object-from-shared-preferences-using-gson-library-in-kotlin/