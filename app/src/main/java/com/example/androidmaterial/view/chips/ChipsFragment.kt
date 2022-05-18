package com.example.androidmaterial.view.chips

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.androidmaterial.R
import com.example.androidmaterial.databinding.ChipsFragmentBinding
import com.example.androidmaterial.view.MainActivity
import com.google.android.material.chip.ChipGroup

class ChipsFragment : Fragment(R.layout.chips_fragment) {

    private lateinit var parentActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = requireActivity() as MainActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val context: Context = ContextThemeWrapper(activity, getStyleName(getCurrentTheme()))
        val localInflater = inflater.cloneInContext(context)
        _binding = ChipsFragmentBinding.inflate(localInflater)
        return binding.root
    }

    private var _binding: ChipsFragmentBinding? = null
    private val binding: ChipsFragmentBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setPrefs(key: String, int: Int) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putInt(key, int)
            apply()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.chipGroup.setOnCheckedStateChangeListener { group, _ ->

            when (group.checkedChipId) {
                R.id.chipMars -> {
                    setPrefs(getString(R.string.THEME_KEY), 1)
                    recreateFragment()
                }

                R.id.chipMercury -> {
                    setPrefs(getString(R.string.THEME_KEY), 2)
                    recreateFragment()
                }

                R.id.chipUranus -> {
                    setPrefs(getString(R.string.THEME_KEY), 3)
                    requireActivity().let { requireActivity().recreate() }
                }

                R.id.chipDefault -> {
                    setPrefs(getString(R.string.THEME_KEY), 4)
                    requireActivity().let { requireActivity().recreate() }
                }

                else -> {
                    Toast.makeText(context, "chosen sss", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.chipForDelete.setOnCloseIconClickListener {
            //binding.chipForDelete.visibility = View.GONE
            binding.chipForDelete.isChecked = false
        }
    }

    private fun recreateFragment() {
        requireActivity().let {
            parentActivity.supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.container, newInstance())
            }
        }
    }

    private fun getCurrentTheme() : Int{
        return requireActivity().getPreferences(Context.MODE_PRIVATE).getInt(getString(R.string.THEME_KEY), -1)
    }

    private fun getStyleName(currentTheme: Int): Int {
        return when (currentTheme) {
            1 -> R.style.Theme_Mars
            2 -> R.style.Theme_Mercury
            3 -> R.style.Theme_Uranus
            4 -> R.style.Theme_AndroidMaterial
            else -> 0
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChipsFragment()

    }
}