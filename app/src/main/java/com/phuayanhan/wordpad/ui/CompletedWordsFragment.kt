package com.phuayanhan.wordpad.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.phuayanhan.wordpad.MainActivity
import com.phuayanhan.wordpad.MyApplication
import com.phuayanhan.wordpad.R
import com.phuayanhan.wordpad.adapters.WordAdapter
import com.phuayanhan.wordpad.databinding.FragmentCompletedWordsBinding
import com.phuayanhan.wordpad.viewModels.CompletedWordViewModel
import com.phuayanhan.wordpad.viewModels.MainViewModel

class CompletedWordsFragment : Fragment() {
    private lateinit var adapter: WordAdapter
    private lateinit var binding: FragmentCompletedWordsBinding

    // this is to call the CompletedWordViewModel for this fragment to call functions in the specific viewModel
    private val viewModel: CompletedWordViewModel by viewModels {
        CompletedWordViewModel.Provider((requireContext().applicationContext as MyApplication).wordRepo)
    }

    // this is to allow main fragment to call functions in completed word fragment Note  !! the sort wont work without this
    private val parentViewModel:MainViewModel by viewModels(
        ownerProducer = {requireParentFragment()}
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // this is to connect the layout from the xml to completed word fragment
        binding= FragmentCompletedWordsBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()

        // this is to show the word
        viewModel.words.observe(viewLifecycleOwner) {
            adapter.setWords(it)
        }
        // this is to update and show the word when you sort
        parentViewModel.refreshCompletedWords.asLiveData().observe(viewLifecycleOwner){
            viewModel.sortWords(it.first,it.second)
        }
    }

    // this function is to refresh the page after changing the database
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
        private var completedWordsFragmentInstance: CompletedWordsFragment? = null
        fun getInstance(): CompletedWordsFragment {
            if (completedWordsFragmentInstance == null) {
                completedWordsFragmentInstance = CompletedWordsFragment()
            }

            return completedWordsFragmentInstance!!
        }
    }

}