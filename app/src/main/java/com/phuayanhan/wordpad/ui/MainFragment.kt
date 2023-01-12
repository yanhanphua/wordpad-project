package com.phuayanhan.wordpad.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SearchView
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.phuayanhan.wordpad.MainActivity
import com.phuayanhan.wordpad.MyApplication
import com.phuayanhan.wordpad.R
import com.phuayanhan.wordpad.adapters.ViewPagerAdapter
import com.phuayanhan.wordpad.databinding.FragmentMainBinding
import com.phuayanhan.wordpad.viewModels.MainViewModel
import com.phuayanhan.wordpad.viewModels.WordsViewModel

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val wordsFragment = WordsFragment.getInstance()
    private val completedWordsFragment = CompletedWordsFragment.getInstance()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ViewPagerAdapter(
            listOf(wordsFragment, completedWordsFragment),
            childFragmentManager,
            lifecycle
        )
        binding.btnSearch.setOnClickListener {
            val search = binding.etSearch.text.toString()
            wordsFragment.refresh(search)
            completedWordsFragment.refresh(search)
        }
//        binding.svSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                p0.let {
//                    wordsFragment.refresh(it.toString())
//                    completedWordsFragment.refresh(it.toString())
//
//                    return false
//                }
//            }
//            override fun onQueryTextChange(p0: String?): Boolean {
//                p0.let {
//                    wordsFragment.refresh(it.toString())
//                    completedWordsFragment.refresh(it.toString())
//                    return false
//                }
//            }
//
//        })
        binding.btnSort.setOnClickListener{
            val dialogBinding = layoutInflater.inflate(R.layout.sort_custom_dialog,null)

            val myDialog = Dialog(requireContext(),R.style.Custom_ThemeOverlay_App_MaterialAlertDialog_Default)
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.show()
            myDialog.findViewById<Button>(R.id.sort).setOnClickListener {
                val rg=myDialog.findViewById<RadioGroup>(R.id.radioGroup1)
                val rg2=myDialog.findViewById<RadioGroup>(R.id.radioGroup2)
                val radioId=rg.checkedRadioButtonId
                val radioId2 = rg2.checkedRadioButtonId
                val radioButton1 = myDialog.findViewById<RadioButton>(radioId)
                val radioButton2 = myDialog.findViewById<RadioButton>(radioId2)
                val radioButtonText1 = radioButton1.text
                val radioButtonText2 = radioButton2.text
                Log.d("idkwtfimdoing","$radioButtonText1,$radioButtonText2")
                viewModel.onRefreshWords(radioButtonText1.toString(),radioButtonText2.toString())
                viewModel.onRefreshCompletedWords(radioButtonText1.toString(),radioButtonText2.toString())
                myDialog.hide()
            }
        }
        binding.vpWordPad.adapter = adapter

        TabLayoutMediator(binding.tlWordPad, binding.vpWordPad) { tab, pos ->
            Log.d("eiyo",pos.toString())
            tab.text = when (pos) {
                0 -> "New Word"
                else -> "Completed Word"
            }
        }.attach()

        setFragmentResultListener("from_add_item") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                wordsFragment.refresh("")
            }
        }
        setFragmentResultListener("from_details") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                wordsFragment.refresh("")
            }
        }
        setFragmentResultListener("from-details-done") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                wordsFragment.refresh("")
                completedWordsFragment.refresh("")
            }
        }
        setFragmentResultListener("from_edit"){_,result->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                wordsFragment.refresh("")
                completedWordsFragment.refresh("")
            }
        }
    }
}