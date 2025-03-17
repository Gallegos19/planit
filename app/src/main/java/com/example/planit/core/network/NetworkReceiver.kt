package com.example.planit.network

import CreateIndividualActivitiesViewModel
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.planit.core.service.SyncService


class NetworkReceiver() : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetworkInfo
            val isConnected = activeNetwork?.isConnectedOrConnecting == true

            if (isConnected) {
                Log.d("NetworkReceiver", "Conexión detectada, iniciando sincronización...")
                val serviceIntent = Intent(context, SyncService::class.java)
                ContextCompat.startForegroundService(context, serviceIntent)
            }
        }
    }
}
