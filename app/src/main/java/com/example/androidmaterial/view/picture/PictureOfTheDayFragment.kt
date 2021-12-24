package com.example.androidmaterial.view.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidmaterial.databinding.PotdFragmentBinding

class PictureOfTheDayFragment:Fragment() {

    private var _binding:PotdFragmentBinding?=null
    val  binding: PotdFragmentBinding
    get() {
        return _binding!!
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
        _binding = PotdFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object{
        fun newInstance() = PictureOfTheDayFragment()
    }

}