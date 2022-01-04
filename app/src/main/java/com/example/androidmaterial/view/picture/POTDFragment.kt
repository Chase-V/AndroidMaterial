package com.example.androidmaterial.view.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.androidmaterial.R
import com.example.androidmaterial.databinding.PotdFragmentBinding
import com.example.androidmaterial.view.BottomNavigationDrawerFragment
import com.example.androidmaterial.view.MainActivity
import com.example.androidmaterial.viewModel.POTDState
import com.example.androidmaterial.viewModel.POTDViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior

class POTDFragment : Fragment() {

    private var _binding: PotdFragmentBinding? = null
    val binding: PotdFragmentBinding
        get() {
            return _binding!!
        }

    private val viewModel: POTDViewModel by lazy {
        ViewModelProvider(this).get(POTDViewModel::class.java)
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

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
        val bsBehavior = BottomSheetBehavior.from(binding.includeBottomSheet.bottomSheetContainer)
        setBottomAppBar()
    }

    private fun renderData(state: POTDState) {
        when (state) {
            is POTDState.Error -> {
                //TODO()
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

    companion object {
        fun newInstance(): POTDFragment {
            return POTDFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.appBarFav -> Toast.makeText(context, "Favourite selected", Toast.LENGTH_LONG)
                .show()
            R.id.appBarSettings -> Toast.makeText(context, "Settings selected", Toast.LENGTH_LONG)
                .show()
            android.R.id.home -> BottomNavigationDrawerFragment().show(
                requireActivity().supportFragmentManager,
                "BNDF"
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private var isMain = true

    private fun setBottomAppBar() {
        val context = activity as MainActivity
        context.setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)

        binding.fab.setOnClickListener {
            if (isMain) {
                isMain = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            }else{
                isMain=true
                binding.bottomAppBar.navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }


    }

}