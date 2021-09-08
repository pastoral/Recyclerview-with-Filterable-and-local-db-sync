package com.hashpeak.bpdbcontacts.ui.contactlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ContactListViewModelFactory (val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactListViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }

}