package com.phuayanhan.wordpad.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.phuayanhan.wordpad.MainActivity
import com.phuayanhan.wordpad.MyApplication
import com.phuayanhan.wordpad.R
import com.phuayanhan.wordpad.adapters.WordAdapter
import com.phuayanhan.wordpad.databinding.FragmentWordsBinding
import com.phuayanhan.wordpad.viewModels.MainViewModel
import com.phuayanhan.wordpad.viewModels.WordsViewModel
import java.util.Date

class WordsFragment : Fragment() {
    private lateinit var adapter: WordAdapter
    private lateinit var binding: FragmentWordsBinding

    // this is allow the fragment to access function from said viewModel
    private val viewModel: WordsViewModel by viewModels {
        WordsViewModel.Provider((requireActivity().application as MyApplication).wordRepo)
    }
    // this is to allow the parent fragment to call function from this fragment
    private val parentViewModel: MainViewModel by viewModels(
        ownerProducer = {requireParentFragment()}
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordsBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()

        // this is to navigate to the add fragment
        binding.efabAddNewItem.setOnClickListener {
            val action = MainFragmentDirections.actionMainToAddWord()
            NavHostFragment.findNavController(this).navigate(action)
        }

        // this is to show current words
        viewModel.words.observe(viewLifecycleOwner) {
            adapter.setWords(it)
        }
        parentViewModel.refreshWords.asLiveData().observe(viewLifecycleOwner){
            viewModel.sortWords(it.first,it.second)
        }
    }

    // this is to refresh when the database is changed
    fun refresh(str:String) {
        viewModel.getWords(str)
    }

    // bind the layout and adapter to the fragment
    fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = WordAdapter(emptyList()) {
            val action = MainFragmentDirections.actionMainToDetails(it.id!!)
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.rvItems.adapter = adapter
        binding.rvItems.layoutManager = layoutManager
    }
    // allow the fragment to behave as a singleton
    companion object {
        private var wordsFragmentInstance: WordsFragment? = null
        fun getInstance(): WordsFragment {
            if (wordsFragmentInstance == null) {
                wordsFragmentInstance = WordsFragment()
            }

            return wordsFragmentInstance!!
        }
    }
}