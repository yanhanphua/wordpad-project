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
    private val viewModel: CompletedWordViewModel by viewModels {
        CompletedWordViewModel.Provider((requireContext().applicationContext as MyApplication).wordRepo)
    }
    private val parentViewModel:MainViewModel by viewModels(
        ownerProducer = {requireParentFragment()}
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentCompletedWordsBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()

        viewModel.words.observe(viewLifecycleOwner) {
            adapter.setWords(it)
        }
        parentViewModel.refreshCompletedWords.asLiveData().observe(viewLifecycleOwner){
            viewModel.sortWords(it.first,it.second)
        }
    }
    fun refresh(str:String) {
        viewModel.getWords(str)

    }

    fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = WordAdapter(emptyList()) {
            val action = MainFragmentDirections.actionMainToDetails(it.id!!)
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.rvItems.adapter = adapter
        binding.rvItems.layoutManager = layoutManager
    }
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