package com.example.androidmaterial.view.chips

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidmaterial.R
import com.example.androidmaterial.databinding.ChipsFragmentBinding
import com.google.android.material.chip.Chip

class ChipsFragment : Fragment(R.layout.chips_fragment) {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChipsFragmentBinding.inflate(inflater, container, false)
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

    fun setPrefs(key:String, int: Int){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt(key, int)
            apply()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedId ->


            when(group.checkedChipId){
                R.id.chipMars -> {
                    setPrefs(getString(R.string.THEME_KEY), 1)
                    requireActivity().let { requireActivity().recreate() }
                }

                R.id.chipMercury -> {
                    setPrefs(getString(R.string.THEME_KEY), 2)
                    requireActivity().let { requireActivity().recreate() }
                }

                R.id.chipUranus -> {
                    setPrefs(getString(R.string.THEME_KEY), 3)
                    requireActivity().let { requireActivity().recreate() }
                }

                R.id.chipDefault -> {
                    setPrefs(getString(R.string.THEME_KEY), 4)
                    requireActivity().let { requireActivity().recreate() }
                }

                else -> {Toast.makeText(context,"chosen sss", Toast.LENGTH_SHORT).show()}
            }

        }

        binding.chipForDelete.setOnCloseIconClickListener {
            //binding.chipForDelete.visibility = View.GONE
            binding.chipForDelete.isChecked = false
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChipsFragment()

    }
}