package com.amol.myapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.amol.myapp.R
import com.amol.myapp.databinding.SpinnerLayoutBinding
import com.amol.myapp.dataclass.Drop_Down_Options

class DropDownArrayAdapter(ctx: Context,
                           list: List<Drop_Down_Options>) :
    ArrayAdapter<Drop_Down_Options>(ctx, 0, list) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        return this.createView(position, view, parent)
    }

    override fun getDropDownView(position: Int, view: View?, parent: ViewGroup): View {
        return this.createView(position, view, parent)
    }

    private fun createView(position: Int, view: View?, parent: ViewGroup): View {

        val dropdown = getItem(position)


        var bindign : SpinnerLayoutBinding  = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.spinner_layout,parent,false)

        bindign.tvTitle.text = dropdown?.view_text

        return bindign.root
    }
}