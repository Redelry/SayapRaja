package com.adrianusid.sayapraja.model

data class AddJobModel(
    val idCorp: String,
    val idJob : String,
    val corpName :String,
    val positionJob : String,
    val description : String,
    val requirement : String,
    val date: String,
    val phone: String
)
