package com.hashpeak.bpdbcontacts.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactDao{
    @Query("select * from contacts")
    fun getContacts(): LiveData<List<DatabaseContacts>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg contacts : DatabaseContacts)
}