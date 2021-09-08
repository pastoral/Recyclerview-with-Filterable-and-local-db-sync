package com.hashpeak.bpdbcontacts.apis

import com.hashpeak.bpdbcontacts.models.Contacts
import com.hashpeak.bpdbcontacts.network.NetworkContacts
import com.hashpeak.bpdbcontacts.network.NetworkContactsContainer
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://bpdb.hash-peak.com/api/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ContactAPIService {
    @GET("AllBPDBContacts")
    fun getContactFromNetwork(): Deferred<NetworkContactsContainer>
}

object ContactsAPI{
    val retrofitService : ContactAPIService by lazy{
        retrofit.create(ContactAPIService::class.java)
    }
}