
package com.phuayanhan.wordpad.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.phuayanhan.wordpad.MyApplication
import com.phuayanhan.wordpad.R
import com.phuayanhan.wordpad.databinding.FragmentDetailsBinding
import com.phuayanhan.wordpad.viewModels.DetailsViewModel


class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var builder : AlertDialog.Builder
    val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.Provider((requireContext().applicationContext as MyApplication).wordRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navArgs: DetailsFragmentArgs by navArgs()

        viewModel.getWordById(navArgs.id)

        viewModel.word.observe(viewLifecycleOwner) {
            binding.run {
                tvTitle.text = it.title
                tvMeaning.text = it.meaning
                tvDetails.text = it.details
                tvSynonym.text = it.synonym
            }
        }

        binding.btnDone.setOnClickListener {
            viewModel.completedWord(navArgs.id)
            val bundle=Bundle()
            bundle.putBoolean("refresh",true)
            setFragmentResult("from-details-done",bundle)
            NavHostFragment.findNavController(this).popBackStack()
        }
        binding.btnUpdate.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsToUpdateWord(navArgs.id)
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding.btnDelete.setOnClickListener {
            val bundle=Bundle()
            bundle.putBoolean("refresh",true)
            val alertDialog= MaterialAlertDialogBuilder(requireContext(), R.style.Custom_ThemeOverlay_App_MaterialAlertDialog_Default)
                .setTitle(binding.tvTitle.text).setMessage(binding.tvMeaning.text)
                .setCancelable(true)
                .setPositiveButton(("Yes")){
                        _, it ->
                    viewModel.deleteWord(navArgs.id)
                    setFragmentResult("from_details",bundle)
                    NavHostFragment.findNavController(this).popBackStack()
                }.setNegativeButton("no"){
                        _, it -> Log.d("halp","eiyo")
                }
                .create()
            alertDialog.show()
//            alertDialog.window?.setGravity(Gravity.CENTER)


        }

    }

}