package com.hashpeak.bpdbcontacts.network

import com.hashpeak.bpdbcontacts.database.DatabaseContacts
import com.hashpeak.bpdbcontacts.models.Contacts
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkContactsContainer(val contacts: List<NetworkContacts>)

@JsonClass(generateAdapter = true)
data class NetworkContacts(
    val id:Int,
    val designition: String,
    val office: String?,
    val zone: String?,
    val phone_1: String?,
    val phone_2: String?
){


}

//fun NetworkContacts.asDomainModel(): List<Contacts>{
//    return listOf(Contacts(
//        id = id,
//        designition = designition,
//        office = office,
//        zone = zone,
//        phone_1 = phone_1,
//        phone_2 = phone_2
//    ))
//}

fun NetworkContactsContainer.asDomainModel(): List<Contacts>{
    return contacts.map {
        Contacts(
            id = it.id,
            designition = it.designition,
            office = it.office,
            zone = it.zone,
            phone_1 = it.phone_1,
            phone_2 = it.phone_2
        )
    }
}

/*
* converts to domain objects from database objects
*  */

fun List<DatabaseContacts>.asDomainModel():List<Contacts>{
    return map {
        Contacts(
            id = it.networkid,
            designition = it.designition,
            office = it.office,
            zone = it.zone,
            phone_1 = it.phone_1,
            phone_2 = it.phone_2
        )
    }
}

/*
* converts to database objects from network objects
*  */

fun NetworkContactsContainer.asDatabaseModel():Array<DatabaseContacts>{
    return contacts.map {
        DatabaseContacts(
            networkid = it.id,
            designition = it.designition,
            office = it.office,
            zone = it.zone,
            phone_1 = it.phone_1,
            phone_2 = it.phone_2
        )
    }.toTypedArray()
}