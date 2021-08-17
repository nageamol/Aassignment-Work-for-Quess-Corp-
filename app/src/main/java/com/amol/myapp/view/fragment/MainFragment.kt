package com.amol.myapp.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.amol.myapp.R
import com.amol.myapp.databinding.MainFragmentBinding
import com.amol.myapp.interfaces.CallbackHandler
import com.amol.myapp.view.fragment.ViewModel.MainViewModel
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.amol.myapp.adapter.RepoListAdapter
import com.amol.myapp.dataclass.RepoOutputModel

class MainFragment : Fragment() {

    lateinit var binding : MainFragmentBinding
    lateinit var bookListAdapter: RepoListAdapter
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }
    val impl = object : CallbackHandler {
        override fun onDone() {

        }

        override fun onStart() {

        }

        override fun onFail(e: Throwable) {

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
        viewModel.callback=impl;
        initRecyclerView()
        loadAPIData()
    }
    fun initRecyclerView(){
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            bookListAdapter = RepoListAdapter()
            adapter =bookListAdapter
        }
    }

    fun loadAPIData() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getBookListObserver().observe(viewLifecycleOwner, Observer<RepoOutputModel>{
            if(it != null) {
                bookListAdapter.formFields = it.form_data.key_form_fields_form_fields.get(0).form_fields
                bookListAdapter.notifyDataSetChanged()
            }
            else {
                Toast.makeText(activity, "Error in fetching data", Toast.LENGTH_SHORT).show()
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