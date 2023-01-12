package com.phuayanhan.wordpad.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.phuayanhan.wordpad.MainActivity
import com.phuayanhan.wordpad.MyApplication
import com.phuayanhan.wordpad.data.Model.Word
import com.phuayanhan.wordpad.databinding.FragmentAddWordBinding
import com.phuayanhan.wordpad.viewModels.AddWordViewModel
import java.text.SimpleDateFormat
import java.util.*


class AddWordFragment : Fragment() {
    private lateinit var binding: FragmentAddWordBinding
    private val viewModel: AddWordViewModel by viewModels {
        AddWordViewModel.Provider((requireActivity().application as MyApplication).wordRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddWordBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // this it to make a call to send data to WordRepository to add a word after the button has been clicked
        binding.btnAdd.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val meaning = binding.etMeaning.text.toString()
            val synonym = binding.etSynonym.text.toString()
            val details = binding.etDetails.text.toString()
            val currentDate: String = SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(Date())
            Log.d("date",currentDate)

            // this is to check if all fields have been filled up and then sends a signal to refresh the main screen to see the result
            if (validate(title, meaning, synonym, details)) {
                viewModel.addWord(Word(null, title, meaning, synonym, details,false,1))
                val bundle = Bundle()
                bundle.putBoolean("refresh", true)
                setFragmentResult("from_add_item", bundle)
                NavHostFragment.findNavController(this).popBackStack()
            } else {
                val snackbar =
                    Snackbar.make(
                        binding.root,
                        "Make sure you fill in everything!",
                        Snackbar.LENGTH_LONG
                    )
                snackbar.show()
            }
        }
    }

    private fun validate(vararg list: String): Boolean {
        for (field in list) {
            if (field.isEmpty()) {
                return false
            }
        }
        return true
    }
}