package com.hashpeak.bpdbcontacts.models

data class Contacts(
    val id:Int,
    val designition: String,
    val office: String?,
    val zone: String?,
    val phone_1: String?,
    val phone_2: String?
){}