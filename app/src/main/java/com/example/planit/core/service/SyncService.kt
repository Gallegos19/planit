package com.example.planit.core.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.planit.R
import com.example.planit.components.left_bar.domain.GetActivityUseCase
import com.example.planit.core.data.SessionManager
import com.example.planit.core.data.local.AppDataContainer
import com.example.planit.core.data.local.personalActivity.entities.PersonalActivity
import com.example.planit.core.data.local.personalActivity.repository.OfflinePersonalActivityInfoRepository
import com.example.planit.views.create_individual_activities.data.model.CreateIndividualActivityDTO
import com.example.planit.views.create_individual_activities.domain.CreateIndividualActivityUseCase
import kotlinx.coroutines.*

class SyncService : Service() {

    private lateinit var repository: OfflinePersonalActivityInfoRepository
    private lateinit var repositoryApi: GetActivityUseCase
    private lateinit var repositoryCreate: CreateIndividualActivityUseCase

    private val serviceScope = CoroutineScope(Dispatchers.IO) // 🔹 Manejo de corutinas seguro

    companion object {
        const val CHANNEL_ID = "sync_service_channel" // 🔹 ID del canal de notificaciones
        const val NOTIFICATION_ID = 1 // 🔹 ID de la notificación
    }

    override fun onCreate() {
        super.onCreate()
        repository = AppDataContainer(applicationContext).personalActivityRepository
        repositoryApi = GetActivityUseCase()
        repositoryCreate = CreateIndividualActivityUseCase()

        createNotificationChannel() // 🔹 Crear el canal de notificaciones
        startForeground(NOTIFICATION_ID, createNotification("Sincronización en curso")) // 🔹 Iniciar en primer plano
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceScope.launch {
            syncActivitiesWithServer()
        }
        return START_STICKY // 🔹 Permite que el servicio se reinicie si el sistema lo detiene
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel() // 🔹 Cancelar corutinas activas para evitar fugas de memoria
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null // 🔹 No es un servicio ligado
    }

    private suspend fun syncActivitiesWithServer() {
        try {
            updateNotification("Verificando actividades para sincronizar...")

            val maxLocalId = repository.findMaxId() ?: 0L
            val activitiesList = repositoryApi.getActivities(SessionManager.getUserId()).getOrNull() ?: emptyList()
            val maxServerId = activitiesList.maxByOrNull { it.activity_id }?.activity_id ?: 0L

            Log.d("Sync", "maxLocalId: $maxLocalId, maxServerId: $maxServerId")

            if (maxLocalId > maxServerId.toLong()) {
                val activitiesToSync = repository.findPersonalActivities().filter { it.activityId > maxServerId.toLong() }

                if (activitiesToSync.isNotEmpty()) {
                    updateNotification("Sincronizando ${activitiesToSync.size} actividades...")
                    sendActivitiesToServer(activitiesToSync)
                    updateNotification("Sincronización completada: ${activitiesToSync.size} actividades sincronizadas")
                } else {
                    updateNotification("No hay actividades nuevas para sincronizar")
                    Log.d("Sync", "No hay actividades nuevas para sincronizar")
                }
            } else {
                updateNotification("Datos actualizados, no es necesario sincronizar")
                Log.d("Sync", "No es necesario sincronizar, los datos están actualizados")
            }

            // Esperar unos segundos para que el usuario pueda ver la notificación final
            delay(3000)
            stopSelf()
        } catch (e: Exception) {
            updateNotification("Error en la sincronización: ${e.message}")
            Log.e("Sync", "Error en la sincronización: ${e.message}")
        }
    }

    private suspend fun sendActivitiesToServer(activities: List<PersonalActivity>) {
        try {
            activities.forEach { activity ->
                try {
                    val activityInfo = repository.findPersonalActivityInfo(activity.activityId)
                    val activityDTO = CreateIndividualActivityDTO(
                        user_id = activity.userId,
                        title = activity.title,
                        category_id = activityInfo.personalActivityInfo.categoryId ?: 0,
                        description = activityInfo.personalActivityInfo.description ?: "",
                        status = activityInfo.personalActivityInfo.status ?: "R",
                        date = activityInfo.personalActivityInfo.dateTo ?: ""
                    )
                    repositoryCreate.createIndividualActivity(activityDTO)
                    Log.d("Sync", "Actividad ${activity.title} sincronizada exitosamente")
                } catch (e: Exception) {
                    Log.e("Sync", "Error al sincronizar actividad ${activity.title}: ${e.message}")
                }
            }
        } catch (e: Exception) {
            Log.e("Sync", "Error al procesar actividades para sincronización: ${e.message}")
        }
    }

    // 🔹 Crear el canal de notificaciones
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Sincronización de Actividades",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    // 🔹 Crear la notificación
    private fun createNotification(contentText: String): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("PlanIt Sync Service")
            .setContentText(contentText)
            .setSmallIcon(R.drawable.logo) // 🔹 Cambia por tu ícono
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    // 🔹 Actualizar la notificación (opcional, si necesitas actualizar el contenido)
    private fun updateNotification(contentText: String) {
        val notification = createNotification(contentText)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}