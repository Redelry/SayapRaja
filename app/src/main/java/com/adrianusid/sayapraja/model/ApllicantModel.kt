package com.adrianusid.sayapraja.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApllicantModel (
    val idPdf : String? = "",
    val userId : String? = "",
    val name : String? = "",
    val phone : String? = "",
    val url : String? ="",
    val idCorp : String? = "",
    val idJob : String? = "",
    val idCorp_idJob : String? = ""
) : Parcelable