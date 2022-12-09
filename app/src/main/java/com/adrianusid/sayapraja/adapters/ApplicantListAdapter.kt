package com.adrianusid.sayapraja.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adrianusid.sayapraja.databinding.ItemJobApplicantBinding
import com.adrianusid.sayapraja.listeners.OnCardClickListener
import com.adrianusid.sayapraja.listeners.OnDeleteApplicantClickListener
import com.adrianusid.sayapraja.listeners.OnDeleteClickListener
import com.adrianusid.sayapraja.listeners.OnPdfClickListener
import com.adrianusid.sayapraja.model.ApllicantModel

class ApplicantListAdapter : RecyclerView.Adapter<ApplicantListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemJobApplicantBinding): RecyclerView.ViewHolder(binding.root)

    private val applicantList = ArrayList<ApllicantModel>()

    private lateinit var onDeleteApplicantClickListener: OnDeleteApplicantClickListener
    private lateinit var onPdfClickListener: OnPdfClickListener
    fun setList(list: List<ApllicantModel>){

        this.applicantList.addAll(list)
        notifyDataSetChanged()
    }



    fun setOnDeleteClickListener(onDeleteApplicantClickListener: OnDeleteApplicantClickListener) {
        this.onDeleteApplicantClickListener = onDeleteApplicantClickListener
    }
    fun setOnPdfClickListener(onPdfClickListener: OnPdfClickListener) {
        this.onPdfClickListener = onPdfClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemJobApplicantBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(applicantList[position]){
                binding.tvApplicantName.text = name
                binding.tvApplicantPhone.text = phone
                binding.delete.setOnClickListener {
                    onDeleteApplicantClickListener.onDeleteClick(applicantList[position])
                    applicantList.clear()
                }
                binding.cardView.setOnClickListener { onPdfClickListener.OnCardClickApplicant(applicantList[position]) }
            }
        }
    }

    override fun getItemCount() = applicantList.size


}