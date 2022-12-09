package com.adrianusid.sayapraja.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.adrianusid.sayapraja.databinding.ItemJobUserBinding
import com.adrianusid.sayapraja.listeners.OnCardClickListener
import com.adrianusid.sayapraja.model.ListJobModel


class JobListAdapterUser : RecyclerView.Adapter<JobListAdapterUser.ViewHolder>() {
    inner class ViewHolder(val binding: ItemJobUserBinding): RecyclerView.ViewHolder(binding.root)

    private val jobList = ArrayList<ListJobModel>()
    private lateinit var onCardClickListener: OnCardClickListener


    fun setJob(list: List<ListJobModel>){
        this.jobList.clear()
        this.jobList.addAll(list)

        notifyDataSetChanged()
    }


    fun setOnCardClickListener(onCardClickListener: OnCardClickListener) {
        this.onCardClickListener = onCardClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemJobUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(jobList[position]){
                binding.tvCompanyName.text = corpName
                binding.tvPosition.text = positionJob
                binding.tvAddDate.text = date
                binding.cardView.setOnClickListener { onCardClickListener.onCardClickListJob(jobList[position]) }

            }
        }
    }


    override fun getItemCount() = jobList.size


}