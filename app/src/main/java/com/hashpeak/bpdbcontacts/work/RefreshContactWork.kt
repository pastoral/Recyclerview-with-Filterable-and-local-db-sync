package com.hashpeak.bpdbcontacts.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hashpeak.bpdbcontacts.database.getDatabase
import com.hashpeak.bpdbcontacts.repositories.ContactRepository
import retrofit2.HttpException

class RefreshContactWork(appContext: Context, params:WorkerParameters)
    :CoroutineWorker(appContext,params){
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = ContactRepository(database)
        return try {
            repository.refreshContact()
            Result.success()
        }
        catch (e:HttpException){
            Result.retry()
        }
    }

    companion object{
        const val WORK_NAME = "RefreshDataWorker"
    }
}
