package com.adrianusid.sayapraja

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrianusid.sayapraja.adapters.ApplicantListAdapter
import com.adrianusid.sayapraja.data.DatabaseReference
import com.adrianusid.sayapraja.databinding.ActivityDetailJobCorpBinding
import com.adrianusid.sayapraja.databinding.ConfirmDialogBinding
import com.adrianusid.sayapraja.listeners.OnDeleteApplicantClickListener
import com.adrianusid.sayapraja.listeners.OnPdfClickListener
import com.adrianusid.sayapraja.model.ApllicantModel
import com.adrianusid.sayapraja.model.ListJobModel
import com.adrianusid.sayapraja.viewmodel.CorpPrefViewModel
import com.adrianusid.sayapraja.viewmodel.JobViewModel
import com.adrianusid.sayapraja.viewmodel.ViewModelFactory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class DetailJobCorpActivity : AppCompatActivity() {

    private val binding: ActivityDetailJobCorpBinding by lazy {
        ActivityDetailJobCorpBinding.inflate(layoutInflater)
    }
    private val ref = DatabaseReference.getDbRef()
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess
    private var corpId: String? = null

    private var adapter: ApplicantListAdapter? = null
    private val _message = MutableLiveData<String>()
    private val jobViewModel: JobViewModel by viewModels()
    val msg: LiveData<String> = _message
    val data = ApllicantModel()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var corpPrefViewModel: CorpPrefViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val dialog = Dialog(this)
        val dialogBinding = ConfirmDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        supportActionBar?.hide()
        adapter = ApplicantListAdapter()
        corpPrefViewModel = obtainPrefCorpPrefViewModel(this)
        corpPrefViewModel.getId().observe(this) {
            corpId = it
            Log.d("IDUSER", it)


            val datas = intent.getParcelableExtra<ListJobModel>(DATA) as ListJobModel

            data.idPdf?.let { idPdf ->
                getApplicantListUser(
                    idPdf, it,
                    datas.idJob.toString()
                )
                Log.d("pdfJOB", "${data.idPdf}")
            }

            Log.d("IDJOB", "${datas.idJob}")


        }

        adapter?.setOnDeleteClickListener(object : OnDeleteApplicantClickListener {
            override fun onDeleteClick(data: ApllicantModel) {
                dialog.show()
                dialogBinding.cancel.setOnClickListener { dialog.dismiss() }
                dialogBinding.save.setOnClickListener {
                   deleteApplicant(data.idPdf.toString())


                    jobViewModel.isSuccess.observe(this@DetailJobCorpActivity) {
                        if (it) {
                            Toast.makeText(
                                this@DetailJobCorpActivity,
                                "Job Deleted",
                                Toast.LENGTH_SHORT
                            ).show()
                            dialog.dismiss()
                            finish()
                            startActivity(intent)
                        }
                    }
                }

            }
        })

                adapter?.setOnPdfClickListener(object : OnPdfClickListener {
                    override fun OnCardClickApplicant(data: ApllicantModel) {
                        val intent = Intent()
                        intent.setDataAndType(Uri.parse(data.url), "application/pdf")
                        intent.action = Intent.ACTION_VIEW
                        startActivity(intent)
                    }

                })


                layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


                binding.rvListJobApplicant.layoutManager = layoutManager
                binding.rvListJobApplicant.setHasFixedSize(true)
                binding.rvListJobApplicant.adapter = adapter


                val data = intent.getParcelableExtra<ListJobModel>(DATA) as ListJobModel
                binding.corpName.text = data.corpName
                binding.phoneNumber.text = data.phone
                binding.tvJobPosition.text = data.positionJob
                binding.tvJobDesc.text = data.description
                binding.tvJobReq.text = data.requirement


            }


            private fun getApplicantListUser(idPdf: String, idCorp: String, idJob: String) {
                val jobRef =
                    ref.child("UploadPdf").child(idPdf).orderByChild("idCorp").equalTo(idCorp)

                jobRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Log.d("IDJOB2", idJob)
                        if (snapshot.exists()) {
                            Log.d("IDJOB3", idJob)

                            for (data in snapshot.children) {

                                val jobs = data.getValue(ApllicantModel::class.java)
                                val jobList = ArrayList<ApllicantModel>()
                                if (jobs?.idJob == idJob) {
                                    jobList.add(jobs)
                                    adapter?.setList(jobList)
                                    Log.d("JOBs", jobs.toString())
                                    Log.d("JOBLIST", jobList.toString())
                                }

                                Log.d("YUKIMURA", jobList.size.toString())
                            }
                        } else {
                            Log.e("Mizuki", "NoDATA")

                        }


                    }

                    override fun onCancelled(error: DatabaseError) {
                        _message.value = error.message
                    }
                })
            }
   private fun deleteApplicant(idPdf: String) {
        val userRef = ref.child("UploadPdf")

        userRef.child(idPdf).setValue(null).addOnCompleteListener {
            _isSuccess.postValue(true)
        }


    }

            companion object {
                const val DATA = "data"
            }

            private fun obtainPrefCorpPrefViewModel(activity: AppCompatActivity): CorpPrefViewModel {
                val factory = ViewModelFactory.getInstance(activity.application)

                return ViewModelProvider(activity, factory)[CorpPrefViewModel::class.java]

            }


}