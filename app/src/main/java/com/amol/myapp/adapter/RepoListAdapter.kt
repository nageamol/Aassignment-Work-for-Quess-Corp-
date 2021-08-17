package com.amol.myapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.api.load
import coil.decode.SvgDecoder
import coil.request.LoadRequest
import com.amol.myapp.MyApp
import com.amol.myapp.R
import com.amol.myapp.const.Constant.Companion.date
import com.amol.myapp.const.Constant.Companion.dropDown
import com.amol.myapp.const.Constant.Companion.editText
import com.amol.myapp.const.Constant.Companion.file
import com.amol.myapp.const.Constant.Companion.logo
import com.amol.myapp.const.Constant.Companion.multiLine
import com.amol.myapp.databinding.*
import com.amol.myapp.dataclass.Form_fields
import com.amol.myapp.view.activity.MainActivity
import java.util.*
import kotlin.collections.ArrayList

class RepoListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var formFields = ArrayList<Form_fields>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        /*  val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_row, parent, false )
           return MyViewHolder(inflater)*/
        when (viewType) {
            logo -> {
                val binding: RecyclerListRowBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.recycler_list_row,
                    parent,
                    false
                )
                return MyViewHolder(binding.getRoot(), binding)
            }
            dropDown -> {
                val binding: RowDropdownBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_dropdown,
                    parent,
                    false
                )
                return DropDownViewHolder(binding.getRoot(), binding)
            }
            file -> {
                val binding: RowFileBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_file,
                    parent,
                    false
                )
                return FileViewHolder(binding.getRoot(), binding)
            }
            editText -> {
                val binding: RowEdittextBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_edittext,
                    parent,
                    false
                )
                return EditTextViewHolder(binding.getRoot(), binding)
            }
            date -> {
                val binding: RowDateBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_date,
                    parent,
                    false
                )
                return DateViewHolder(binding.getRoot(), binding)
            }
            multiLine -> {
                val binding: RowMultilineBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_multiline,
                    parent,
                    false
                )
                return MultiLineViewHolder(binding.getRoot(), binding)
            }
            else -> {
                val binding: RecyclerListRowBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.recycler_list_row,
                    parent,
                    false
                )
                return MyViewHolder(binding.getRoot(), binding)
            }
        }

    }

    public fun clear() {
        formFields.clear()
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        when (formFields.get(position).viewType) {
            logo -> {

                (holder as MyViewHolder).bind(formFields[position])
            }
            dropDown -> {
                (holder as DropDownViewHolder).bind(formFields[position])
            }
            file -> {

                (holder as FileViewHolder).bind(formFields[position])
            }
            editText -> {
                (holder as EditTextViewHolder).bind(formFields[position])
            }
            date -> {

                (holder as DateViewHolder).bind(formFields[position])
            }
            multiLine -> {

                (holder as MultiLineViewHolder).bind(formFields[position])
            }
            else -> {
            }
        }
    }

    override fun getItemCount(): Int {
        return formFields.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {


        return formFields.get(position).viewType
    }

    class MyViewHolder(root: View, binding: RecyclerListRowBinding) :
        RecyclerView.ViewHolder(root) {
        var binding: RecyclerListRowBinding = binding

        fun bind(data: Form_fields) {
            binding.thumbImageView.loadSvgOrOthers(data.text_ans)
            binding.tvTitle.text = data.label

        }

    }

    class FileViewHolder(root: View, binding: RowFileBinding) : RecyclerView.ViewHolder(root) {
        var binding: RowFileBinding = binding
        fun bind(data: Form_fields) {
            binding.tvTitle.text = data.label
        }
    }

    class DateViewHolder(root: View, binding: RowDateBinding) : RecyclerView.ViewHolder(root) {
        var binding: RowDateBinding = binding
        fun bind(data: Form_fields) {

            binding.tvTitle.text = data.label
        }
    }

    class DropDownViewHolder(root: View, binding: RowDropdownBinding) :
        RecyclerView.ViewHolder(root) {
        var binding: RowDropdownBinding = binding
        fun bind(data: Form_fields) {
            binding.spinner.adapter = DropDownArrayAdapter(
                MyApp.instance,
                data.dropDownOptions
            )
            binding.tvTitle.text = data.label

        }
    }

    class EditTextViewHolder(root: View, binding: RowEdittextBinding) :
        RecyclerView.ViewHolder(root) {
        var binding: RowEdittextBinding = binding
        fun bind(data: Form_fields) {

            binding.tvTitle.text = data.label
        }
    }

    class MultiLineViewHolder(root: View, binding: RowMultilineBinding) :
        RecyclerView.ViewHolder(root) {
        var binding: RowMultilineBinding = binding
        fun bind(data: Form_fields) {

            binding.tvTitle.text = data.label
        }
    }
}

fun ImageView.loadSvgOrOthers(myUrl: String?) {
    myUrl?.let {
        if (it.toLowerCase(Locale.ENGLISH).endsWith("svg")) {
            val imageLoader = ImageLoader.Builder(this.context)
                .componentRegistry {
                    add(SvgDecoder(this@loadSvgOrOthers.context))
                }
                .build()
            val request = LoadRequest.Builder(this.context)
                .data(it)
                .target(this)
                .build()
            imageLoader.execute(request)
        } else {
            this.load(myUrl)
        }
    }
}