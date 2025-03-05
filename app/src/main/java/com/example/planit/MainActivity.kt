package com.example.planit

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.planit.core.data.GlobalStorage
import com.example.planit.core.data.SessionManager
import com.example.planit.core.navigation.NavigationWrapper
import com.example.planit.ui.theme.PlanitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SessionManager.init(this)
        GlobalStorage.init(this)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pedirPermisoNotificacion()
        }

        setContent {
            PlanitTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    NavigationWrapper(Modifier.padding(innerPadding))
                }
            }
        }
    }

    private fun pedirPermisoNotificacion() {
        val permiso = Manifest.permission.POST_NOTIFICATIONS

        if (ContextCompat.checkSelfPermission(this, permiso) == PackageManager.PERMISSION_GRANTED) {
            Log.d("dbug", "Permiso de notificación concedido")
        } else {
            requestPermissionLauncher.launch(permiso)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show()
                Log.d("dbug", "El usuario concedió el permiso de notificaciones")
            } else {
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
                Log.d("dbug", "El usuario denegó el permiso de notificaciones")
            }
        }
}
