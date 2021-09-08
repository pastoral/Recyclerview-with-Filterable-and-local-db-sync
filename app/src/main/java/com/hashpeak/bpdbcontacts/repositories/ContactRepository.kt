package com.hashpeak.bpdbcontacts.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.hashpeak.bpdbcontacts.apis.ContactsAPI
import com.hashpeak.bpdbcontacts.database.ContactsDB
import com.hashpeak.bpdbcontacts.models.Contacts
import com.hashpeak.bpdbcontacts.network.asDatabaseModel
import com.hashpeak.bpdbcontacts.network.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/* Repository for fetching data from the network and store data into SQLite DB */
class ContactRepository(private val contacsDB:ContactsDB) {


    /**
     * Refresh the contacts stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     *
     */
    suspend public fun refreshContact(){
        withContext(Dispatchers.IO){
            val contactlist = ContactsAPI.retrofitService.getContactFromNetwork().await()
            contacsDB.contactDao.insertAll(*contactlist.asDatabaseModel())
        }
    }

    /**
     * A contactlist of Contacts that can be shown on the screen.
     */

    val contacts : LiveData<List<Contacts>> = Transformations.map(contacsDB.contactDao.getContacts()){
        it.asDomainModel()
    }
}