package com.example.androidmaterial.view.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.androidmaterial.R
import com.example.androidmaterial.databinding.PotdFragmentBinding
import com.example.androidmaterial.viewModel.PictureOfTheDayState
import com.example.androidmaterial.viewModel.PictureOfTheDayViewModel

class PictureOfTheDayFragment:Fragment() {

    private var _binding:PotdFragmentBinding?=null
    val  binding: PotdFragmentBinding
    get() {
        return _binding!!
    }

    private val viewModel:PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendServerRequest()

        binding.inputLayout.setEndIconOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
    }

    private fun renderData(state:PictureOfTheDayState){
        when(state){
            is PictureOfTheDayState.Error -> {
                //TODO()
            }
            is PictureOfTheDayState.Loading -> {
                binding.imageView.load(R.drawable.ic_no_photo_vector)
            }
            is PictureOfTheDayState.Success -> {
                val pictureOfTheDayResponseData = state.pictureOfTheDayResponseData
                val url = pictureOfTheDayResponseData.url
                binding.imageView.load(url){
                    lifecycle(this@PictureOfTheDayFragment)
                    error(R.drawable.ic_load_error_vector)
                    placeholder(R.drawable.ic_no_photo_vector)
                }
            }
        }
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