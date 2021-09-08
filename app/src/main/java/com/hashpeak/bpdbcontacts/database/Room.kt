package com.hashpeak.bpdbcontacts.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(DatabaseContacts::class), version = 1, exportSchema = false)
abstract class ContactsDB : RoomDatabase() {
    abstract val contactDao: ContactDao
}

private lateinit var INSTANCE : ContactsDB

fun getDatabase(context: Context):ContactsDB{
    synchronized(ContactsDB::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ContactsDB::class.java,"contacts").build()
        }
    }

    return INSTANCE
}

