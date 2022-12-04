package com.adrianusid.sayapraja.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adrianusid.sayapraja.databinding.ItemJobBinding
import com.adrianusid.sayapraja.listeners.OnDeleteClickListener
import com.adrianusid.sayapraja.listeners.OnEditClickListener
import com.adrianusid.sayapraja.model.ListJobModel

class JobListAdapter : RecyclerView.Adapter<JobListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemJobBinding): RecyclerView.ViewHolder(binding.root)

    private val jobList = ArrayList<ListJobModel>()
    private lateinit var onEditClickListener : OnEditClickListener
    private lateinit var onDeleteClickListener: OnDeleteClickListener

    fun setJob(list: List<ListJobModel>){
        this.jobList.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnEditClickListener(onEditClickListener: OnEditClickListener) {
        this.onEditClickListener = onEditClickListener
    }

    fun setOnDeleteClickListener(onDeleteClickListener: OnDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemJobBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(jobList[position]){
                binding.tvCompanyName.text = corpName
                binding.tvPosition.text = positionJob
                binding.tvAddDate.text = date

                binding.edit.setOnClickListener { onEditClickListener.onEditClick(jobList[position]) }
                binding.delete.setOnClickListener {
                    onDeleteClickListener.onDeleteClick(jobList[position])
                    jobList.clear()
                }
            }
        }
    }

    override fun getItemCount() = jobList.size


}