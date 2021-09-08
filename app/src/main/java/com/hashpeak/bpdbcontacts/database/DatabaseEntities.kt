package com.hashpeak.bpdbcontacts.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class DatabaseContacts constructor(
    @PrimaryKey
    val networkid: Int,
    val designition: String,
    val office: String?,
    val zone: String?,
    val phone_1: String?,
    val phone_2: String?
)