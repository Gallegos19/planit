import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.planit.R
import kotlinx.coroutines.launch

@Composable
fun LeftBar(
    onClose: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Column(
        modifier = Modifier
            .width(screenWidth * 0.75f)
            .fillMaxHeight()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.planit_logo),
                contentDescription = "logo",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))
            DrawerItem("Inicio", Icons.Default.Home) {
                scope.launch { onClose() }
                onNavigate("home")
            }
            DrawerItem("Tu Espacio", Icons.Default.Add) {
                scope.launch { onClose() }
                onNavigate("yourSpace")
            }
            DrawerItem("Espacios de Equipo", Icons.Default.Group) {
                scope.launch { onClose() }
                onNavigate("teamSpaces")
            }
        }


        Button(
            onClick = { onClose() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            logout()
        }
    }
}

@Composable
fun logout() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center // Centrar elementos
    ) {
        Text("Cerrar Sesión", color = Color.White) // Asegurar color blanco para visibilidad
        Spacer(modifier = Modifier.width(8.dp)) // Espaciado mejorado
        Icon(imageVector = Icons.Default.Logout, contentDescription = "Cerrar Sesión", tint = Color.White)
    }
}

@Composable
fun DrawerItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.Black)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = Color.Black)
    }
}
