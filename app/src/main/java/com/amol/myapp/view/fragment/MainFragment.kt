package com.amol.myapp.view.fragment

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.amol.myapp.R
import com.amol.myapp.databinding.MainFragmentBinding
import com.amol.myapp.interfaces.CallbackHandler
import com.amol.myapp.view.fragment.ViewModel.MainViewModel
import com.amol.myapp.adapter.RepoListAdapter
import com.amol.myapp.const.Constant
import com.amol.myapp.const.Constant.Companion.logo
import com.amol.myapp.dataclass.Form_fields
import com.amol.myapp.dataclass.RepoOutputModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    lateinit var binding : MainFragmentBinding
    lateinit var repoListAdapter: RepoListAdapter
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }
   val callbackHandler= object : MainViewModel.CallBack{
        override fun onClick(view: View) {
            if (view.id == R.id.btnRetry){
                loadData()
            }
        }

    }
    val impl = object : CallbackHandler {
        override fun onDone() {
            binding.swipeView.isRefreshing = false
            binding.progressBar.visibility =  View.GONE
        }

        override fun onStart() {
            binding.layoutError.visibility =  View.GONE
            binding.progressBar.visibility =  View.VISIBLE
        }

        override fun onFail(e: Throwable) {
            binding.layoutError.visibility =  View.VISIBLE
            binding.progressBar.visibility =  View.GONE
            val snackbar = Snackbar.make(binding.root, "Something went wrong..",
                    Snackbar.LENGTH_LONG).setAction("Ok", null)
            snackbar.show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater,R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider (this).get(MainViewModel::class.java)
        binding.viewModelData = viewModel;
        viewModel.callback=impl;
        viewModel.mainFragmentCallback = callbackHandler
        initRecyclerView()
        initSwipe()
        loadData()
    }

    private fun initSwipe() {
        binding.swipeView.setOnRefreshListener {
            repoListAdapter.clear()
            loadData()
            binding.swipeView.isRefreshing = true
        }

    }

    fun initRecyclerView(){
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            repoListAdapter = RepoListAdapter()
            adapter =repoListAdapter
        }
    }

    fun loadData() {

        viewModel.getRepoListObserver().observe(viewLifecycleOwner, Observer<RepoOutputModel>{
            if(it != null) {
                for (item in  it.form_data.key_form_fields_form_fields.get(0).form_fields) {

                    when (item.field_type) {
                        Constant.LOGO -> {
                            item.viewType = logo
                        }
                        Constant.DROP_DOWN -> {
                            item.viewType =  Constant.dropDown
                        }
                        Constant.FILE -> {
                            item.viewType =  Constant.file
                        }
                        Constant.EDIT_BOX -> {
                            item.viewType =  Constant.editText
                        }
                        Constant.DATE -> {
                            item.viewType =  Constant.date
                        }
                        Constant.MULTI_LINE -> {
                            item.viewType =  Constant.multiLine
                        }
                        else -> {
                            item.viewType =  0
                        }

                    }
                }


                    repoListAdapter.formFields = it.form_data.key_form_fields_form_fields.get(0).form_fields
                repoListAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getRepoFromServer()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (viewModel!=null){
            viewModel.onDestroy()
        }
    }
}