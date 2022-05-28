package com.hakretcode.stickynote.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hakretcode.stickynote.R
import com.hakretcode.stickynote.ui.form.FormFragment
import com.hakretcode.stickynote.ui.main.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, HomeFragment(), HomeFragment.TAG)
            .commit()
        else {
            val fragment = supportFragmentManager.findFragmentByTag(FormFragment.TAG)
            if (fragment != null) supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        val formFragment = fragmentManager.findFragmentByTag(FormFragment.TAG)
        if (formFragment != null) fragmentManager.beginTransaction().remove(formFragment).commit()
        else super.onBackPressed()
    }
}