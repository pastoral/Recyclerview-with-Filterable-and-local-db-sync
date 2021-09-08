package com.hashpeak.bpdbcontacts

import android.app.Application
import android.os.Build
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hashpeak.bpdbcontacts.work.RefreshContactWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ContactsApp : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }
    private fun delayedInit(){
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork(){
        val constraints = Constraints.Builder()
            //.setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)

            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshContactWork>(
            1,
            TimeUnit.DAYS,
        )   .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshContactWork.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}