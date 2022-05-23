package com.example.androidmaterial.view

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.androidmaterial.R
import com.example.androidmaterial.databinding.ActivityMainBinding
import com.example.androidmaterial.databinding.PotdFragmentBinding
import com.example.androidmaterial.view.chips.ChipsFragment
import com.example.androidmaterial.view.picture.POTDFragment
import com.google.android.material.bottomappbar.BottomAppBar
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var _binding: ActivityMainBinding? = null

    private val binding: ActivityMainBinding
        get() {
            return _binding!!
        }

    private var isMain = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getPreferences(MODE_PRIVATE)
        when (prefs.getInt(getString(R.string.THEME_KEY), -1)) {
            1 -> setTheme(R.style.Theme_Mars)
            2 -> setTheme(R.style.Theme_Mercury)
            3 -> setTheme(R.style.Theme_Uranus)
            4 -> setTheme(R.style.Theme_AndroidMaterial)
            else -> setTheme(R.style.Theme_Material3_DayNight_NoActionBar)
        }

        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        savedInstanceState.let {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.container, POTDFragment())
            }
        }

        setBottomAppBar()
    }

    private fun setBottomAppBar() {
        this.setSupportActionBar(binding.bottomAppBar)

        val calendarDatePickerDialog = DatePickerDialog(this)

        binding.fab.setOnClickListener {
            if (isMain) {
                calendarDatePickerDialog.show()
                calendarDatePickerDialog.setOnDateSetListener { datePicker, myear, mmonth, mdayOfMonth ->
                    val calendar = Calendar.getInstance()



                    Toast.makeText(this, "$myear + $mmonth + $mdayOfMonth" , Toast.LENGTH_SHORT).show()

                }

                isMain = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(this, R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_baseline_history_24
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bottom_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.appBarFav -> Toast.makeText(this, "Favourite selected", Toast.LENGTH_LONG)
                .show()

            R.id.appBarSettings -> startFragment(ChipsFragment.newInstance(), "Chips")


            android.R.id.home -> BottomNavigationDrawerFragment().show(
                supportFragmentManager,
                "BNDF"
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startFragment(fragment: Fragment, backstackTag: String) {

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.container,
                fragment
            )
            addToBackStack(backstackTag)
        }

    }

    override fun recreate() {
        finish();
        overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )
        startActivity(intent)
        overridePendingTransition(
            R.anim.slide_up,
            R.anim.slide_down
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}