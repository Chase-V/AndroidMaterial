package com.example.androidmaterial.view

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.commit
import com.example.androidmaterial.R
import com.example.androidmaterial.view.picture.POTDFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPrefs = getPreferences(MODE_PRIVATE)
        val chipGroup = findViewById<ChipGroup>(R.id.chipGroup)
        when (sharedPrefs.getInt(getString(R.string.THEME_KEY), -1)) {
            1 -> setTheme(R.style.Theme_Mars)
            2 -> setTheme(R.style.Theme_Mercury)
            3 -> setTheme(R.style.Theme_Uranus)
            4 -> setTheme(R.style.Theme_AndroidMaterial)
            else -> setTheme(R.style.Theme_Material3_DayNight_NoActionBar)
        }
        setContentView(R.layout.activity_main)
        savedInstanceState.let {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.container, POTDFragment())
            }

        }
    }

}