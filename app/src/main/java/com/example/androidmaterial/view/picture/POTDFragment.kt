package com.example.androidmaterial.view.picture

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.androidmaterial.R
import com.example.androidmaterial.databinding.PotdFragmentBinding
import com.example.androidmaterial.view.BottomNavigationDrawerFragment
import com.example.androidmaterial.view.MainActivity
import com.example.androidmaterial.view.chips.ChipsFragment
import com.example.androidmaterial.viewModel.POTDState
import com.example.androidmaterial.viewModel.POTDViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior


class POTDFragment : Fragment(R.layout.potd_fragment) {

    companion object {
        fun newInstance(): POTDFragment {
            return POTDFragment()
        }
    }

    private var _binding: PotdFragmentBinding? = null

    private val binding: PotdFragmentBinding
        get() {
            return _binding!!
        }

    private val viewModel: POTDViewModel by lazy {
        ViewModelProvider(this).get(POTDViewModel::class.java)
    }

    private var fragmentTheme :Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentTheme = getCurrentTheme()
        Log.d(TAG, "onCreate: fragment $fragmentTheme")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (fragmentTheme != getCurrentTheme()){
            requireActivity().recreate()
        }

        val bsBehavior = BottomSheetBehavior.from(binding.includeBottomSheet.bottomSheetContainer)

        if (bsBehavior.state == BottomSheetBehavior.STATE_EXPANDED){
            requireActivity().onBackPressedDispatcher.addCallback {
                bsBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        viewModel.getData().observe(viewLifecycleOwner) {
            renderData(it)
        }

        viewModel.sendServerRequest()

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        binding.imageView.setOnClickListener {
            bsBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun renderData(state: POTDState) {
        when (state) {
            is POTDState.Error -> {
                Toast.makeText(context, "Нет ответа от сервера :(", Toast.LENGTH_LONG).show()
                binding.imageView.load(R.drawable.net_interneta)
            }
            is POTDState.Loading -> {
                binding.imageView.load(R.drawable.ic_no_photo_vector)
            }
            is POTDState.Success -> {
                val pictureOfTheDayResponseData = state.pictureOfTheDayResponseData
                val url = pictureOfTheDayResponseData.url
                binding.imageView.load(url) {
                    lifecycle(this@POTDFragment)
                    error(R.drawable.ic_load_error_vector)
                    placeholder(R.drawable.ic_no_photo_vector)
                }
                binding.includeBottomSheet.bottomSheetDescriptionHeader.text =
                    pictureOfTheDayResponseData.title
                binding.includeBottomSheet.bottomSheetDescription.text =
                    pictureOfTheDayResponseData.explanation
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().setTheme(getCurrentTheme())
        _binding = PotdFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getCurrentTheme(): Int {
        return requireActivity().getPreferences(Context.MODE_PRIVATE)
            .getInt(getString(R.string.THEME_KEY), -1)
    }

    private fun recreateFragment(backstackTag: String) {
        requireActivity().let {
            it.supportFragmentManager.popBackStack()
            it.supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.container, newInstance(), backstackTag)
                addToBackStack(backstackTag)
            }
        }
    }
}