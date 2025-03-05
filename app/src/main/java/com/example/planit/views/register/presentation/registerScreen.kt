package com.example.planit.views.register.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planit.components.Logo
import com.example.planit.components.Title
import com.example.planit.views.register.data.model.CreateUserRequest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel,
    navigateToLogin: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(60.dp))
                    Logo()
                    Forms(registerViewModel, navigateToLogin)
                    ToLogin(navigateToLogin)
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Forms(registerViewModel: RegisterViewModel, navigateToLogin: () -> Unit) {
    val nombre by registerViewModel.nombre.observeAsState("")
    val apellido by registerViewModel.apellido.observeAsState("")
    val email by registerViewModel.correo.observeAsState("")
    val password by registerViewModel.password.observeAsState("")
    val success by registerViewModel.success.observeAsState(false)
    val error by registerViewModel.error.observeAsState("")

    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .shadow(8.dp, shape = MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title("Registrarse")

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = nombre,
            onValueChange = { registerViewModel.onChangeNombre(it) },
            label = { Text("Nombre(s)", color = Color.Black) },
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text("Ingresa tu nombre") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Nombre", tint = Color.Black) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            colors = textFieldColors()
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = apellido,
            onValueChange = { registerViewModel.onChangeApellido(it) },
            label = { Text("Apellido(s)", color = Color.Black) },
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text("Ingresa tu apellido") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Apellido", tint = Color.Black) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            colors = textFieldColors()
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = email,
            onValueChange = { registerViewModel.onChangeCorreo(it) },
            label = { Text("Correo electrónico", color = Color.Black) },
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text("Ingresa tu correo") },
            leadingIcon = { Icon(Icons.Default.AlternateEmail, contentDescription = "Correo", tint = Color.Black) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            colors = textFieldColors()
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = password,
            onValueChange = { registerViewModel.onChangePassword(it) },
            label = { Text("Contraseña", color = Color.Black) },
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text("Ingresa tu contraseña", color = Color.DarkGray) },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Contraseña", tint = Color.Black) },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                        tint = Color.Black
                    )
                }
            },
            colors = textFieldColors()
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                val user = CreateUserRequest(nombre, apellido, email, password)
                registerViewModel.onClick(user)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color.Black),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Registrarse", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (error.isNotEmpty()) {
            Text(error, color = Color.Red)
        }

        if (success) {
            LaunchedEffect(success) {
                navigateToLogin()
            }
        }
    }
}


// Función para definir los colores del TextField de manera uniforme
@Composable
fun textFieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    disabledContainerColor = Color.White,
    focusedTextColor = Color.Black,
    unfocusedTextColor = Color.Black,
    focusedPlaceholderColor = Color.Black,
    unfocusedPlaceholderColor = Color.Black
)

@Composable
fun ToLogin(navigateToLogin: () -> Unit) {
    Spacer(modifier = Modifier.height(30.dp))
    Row {
        Text("¿Ya tienes cuenta?", color = Color.Gray, fontSize = 15.sp)
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            "Inicia Sesión aquí",
            color = Color.Black,
            fontSize = 15.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.clickable { navigateToLogin() }
        )
    }
}
