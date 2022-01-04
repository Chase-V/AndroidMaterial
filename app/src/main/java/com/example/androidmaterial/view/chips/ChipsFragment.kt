package com.example.androidmaterial.view.chips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidmaterial.databinding.ChipsFragmentBinding
import com.google.android.material.chip.Chip

class ChipsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChipsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var _binding: ChipsFragmentBinding? = null
    val binding: ChipsFragmentBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            group.findViewById<Chip>(checkedId)?.let{
                Toast.makeText(context,"choose ${it.text}", Toast.LENGTH_SHORT).show()
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