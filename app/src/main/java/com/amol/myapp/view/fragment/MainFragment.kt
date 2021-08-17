package com.amol.myapp.view.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.amol.myapp.R
import com.amol.myapp.adapter.RepoListAdapter
import com.amol.myapp.const.Constant
import com.amol.myapp.const.Constant.Companion.logo
import com.amol.myapp.databinding.MainFragmentBinding
import com.amol.myapp.dataclass.RepoOutputModel
import com.amol.myapp.interfaces.CallbackHandler
import com.amol.myapp.view.fragment.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    lateinit var binding: MainFragmentBinding
    lateinit var repoListAdapter: RepoListAdapter
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    private val callbackHandler = object : MainViewModel.CallBack {
        override fun onClick(view: View) {
            if (view.id == R.id.btnRetry) {
                loadData()
            }
        }

    }
    private val impl = object : CallbackHandler {
        override fun onDone() {
            binding.swipeView.isRefreshing = false
            binding.progressBar.visibility = View.GONE
        }

        override fun onStart() {
            binding.layoutError.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }

        override fun onFail(e: Throwable) {
            binding.layoutError.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            showSnackBar("Something went wrong..")
        }

    }

    fun showSnackBar(text: String) {
        val snackbar = Snackbar.make(
            binding.root, text,
            Snackbar.LENGTH_LONG
        ).setAction("Ok", null)
        snackbar.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModelData = viewModel;
        viewModel.callback = impl;
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

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            repoListAdapter = RepoListAdapter()
            adapter = repoListAdapter
        }
    }

    fun loadData() {
        if (!isOnline(requireActivity())) {
            showSnackBar("No Internet Connection..")
            binding.layoutError.visibility = (View.VISIBLE)
            return
        }

        viewModel.getRepoListObserver().observe(viewLifecycleOwner, Observer<RepoOutputModel> {
            if (it != null) {
                for (item in it.form_data.key_form_fields_form_fields.get(0).form_fields) {

                    when (item.field_type) {
                        Constant.LOGO -> {
                            item.viewType = logo
                        }
                        Constant.DROP_DOWN -> {
                            item.viewType = Constant.dropDown
                        }
                        Constant.FILE -> {
                            item.viewType = Constant.file
                        }
                        Constant.EDIT_BOX -> {
                            item.viewType = Constant.editText
                        }
                        Constant.DATE -> {
                            item.viewType = Constant.date
                        }
                        Constant.MULTI_LINE -> {
                            item.viewType = Constant.multiLine
                        }
                        else -> {
                            item.viewType = 0
                        }

                    }
                }


                repoListAdapter.formFields =
                    it.form_data.key_form_fields_form_fields.get(0).form_fields
                repoListAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getRepoFromServer()
    }

    private fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                //It will check for both wifi and cellular network
                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            }
            return false
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (viewModel != null) {
            viewModel.onDestroy()
        }
    }
}