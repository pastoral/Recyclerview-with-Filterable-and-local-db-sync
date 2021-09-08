package com.hashpeak.bpdbcontacts.ui.contactlist

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.hashpeak.bpdbcontacts.apis.ContactsAPI
import com.hashpeak.bpdbcontacts.database.getDatabase
import com.hashpeak.bpdbcontacts.models.Contacts
import com.hashpeak.bpdbcontacts.network.asDomainModel
import com.hashpeak.bpdbcontacts.repositories.ContactRepository
import com.hashpeak.bpdbcontacts.utils.APIStatus
import com.hashpeak.bpdbcontacts.utils.isOnline
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

//ContactListViewModel(application: Application) : AndroidViewModel(application)
class ContactListViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<APIStatus>()
    val status: LiveData<APIStatus> get() = _status


    private val _navigateToSelectedProperty = MutableLiveData<Contacts?>()
    val navigateToSelectedProperty : LiveData<Contacts?> get() = _navigateToSelectedProperty



//    private val _contactlist = MutableLiveData<List<Contacts>>()
//    val contactlist: LiveData<List<Contacts>>
//        get() = _contactlist

    private val database = getDatabase(application)
    private val repository = ContactRepository(database)

    init {
        if(isOnline(application)){
            try {
                viewModelScope.launch {
                    repository.refreshContact()
                }
            }
            catch (e:HttpException){
                Toast.makeText(application,"Offline version", Toast.LENGTH_SHORT).show()
            }
        }


        //refreshContactDataFromNetwork()

    }
    val contactlist = repository.contacts

//    private fun refreshContactDataFromNetwork() = viewModelScope.launch {
//        try {
//            val contactlist = ContactsAPI.retrofitService.getContactFromNetwork().await()
//            _contactlist.postValue(contactlist.asDomainModel())
//        } catch (networkError: IOException) {
//            _status.value = APIStatus.ERROR
//            // Show an infinite loading spinner if the request fails
//            // challenge exercise: show an error to the user if the network request fails
//        }
//    }

    fun displayContactDetails(contact: Contacts){
        _navigateToSelectedProperty.value = contact
        Log.d("ITEMCHECK", contact.id.toString())
    }

    fun displayPropertyDetailsComplete(){
        _navigateToSelectedProperty.value = null
    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ContactListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ContactListViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }



}




