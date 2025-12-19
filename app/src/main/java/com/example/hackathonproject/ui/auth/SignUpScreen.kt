package com.example.hackathonproject.ui.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hackathonproject.viewmodel.AuthState
import com.example.hackathonproject.viewmodel.AuthViewModel


@Composable
fun SignUpScreen(modifier: Modifier = Modifier,navController: NavController, authViewModel: AuthViewModel) {
    var password by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var semester by remember { mutableStateOf("Semester 2") }

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value){
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(context,(authState.value as AuthState.Error).message,Toast.LENGTH_SHORT)
            else -> Unit
        }

    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF0F4FC))
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                navController.navigate("login"){
                    popUpTo("signup"){inclusive = true}
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Create Account",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Logo
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF2B5CFA),
            modifier = Modifier.size(80.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Logo",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Fill in your details to register.",
            fontSize = 14.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Form Fields
        RegistrationInputField(
            label = "Enrollment Number",
            value = password,
            onValueChange = { password = it },
            placeholder = "e.g.  256150307XXX"
        )

        Spacer(modifier = Modifier.height(16.dp))

        RegistrationInputField(
            label = "Full Name",
            value = fullName,
            onValueChange = { fullName = it },
            placeholder = "John Doe"
        )

        Spacer(modifier = Modifier.height(16.dp))

        RegistrationInputField(
            label = "Email ID",
            value = email,
            onValueChange = { email = it },
            placeholder = "your_email@gmail.com"
        )

        Spacer(modifier = Modifier.height(16.dp))

        RegistrationInputField(
            label = "Semester",
            value = semester,
            onValueChange = { semester = it },
            placeholder = ""
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Register Button
        Button(
            onClick = {
                authViewModel.signup(email,password)
            },
            enabled = authState.value != AuthState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2B5CFA)
            )
        ) {
            Text(
                text = "Register",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(
            onClick = {
                navController.navigate("login")
            },
            interactionSource = interactionSource
        ) {
            val annotatedString = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append("Already have an account? ")
                }

                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF2B5CFA),
                        fontWeight = FontWeight.Bold,
                        textDecoration = if (isHovered) TextDecoration.Underline else TextDecoration.None
                    )
                ) {
                    append("Login")
                }
            }
            Text(text = annotatedString)
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun RegistrationInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.LightGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color(0xFFE0E0E0),
                unfocusedIndicatorColor = Color(0xFFE0E0E0)
            )
        )
    }
}
