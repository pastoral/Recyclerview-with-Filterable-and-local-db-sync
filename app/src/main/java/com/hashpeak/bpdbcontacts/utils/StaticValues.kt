package com.hashpeak.bpdbcontacts.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import java.util.regex.Pattern

enum class APIStatus{LOADING, ERROR, DONE}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}

fun phoneNumberMod(str: String?) : String?{
    val PHONE_PATTERN = Pattern.compile("""^[_A-z0-9]*((\s)*[_A-z0-9])*${'$'}""")

    var words = str.toString().trim()
    var numberOfInputWords = words.split("\\s+".toRegex()).size

    if(PHONE_PATTERN.matcher(words).matches()){
        //if(numberOfInputWords>1 || numberOfInputWords.is )
        if(numberOfInputWords<11){
            words = "0"+words
        }
    }

    return words
}