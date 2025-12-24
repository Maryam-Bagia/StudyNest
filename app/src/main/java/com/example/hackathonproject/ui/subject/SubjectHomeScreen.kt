package com.example.hackathonproject.ui.subject

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hackathonproject.data.model.MaterialType
import com.example.hackathonproject.ui.theme.HackathonProjectTheme
import com.example.hackathonproject.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectHomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    subjectId: String?,
    subjectName: String?,
    subjectCode: String?,
    isDarkMode: Boolean
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    HackathonProjectTheme(darkTheme = isDarkMode) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier.width(300.dp),
                ) {
                    SubjectDrawerContent(
                        onCloseClick = { scope.launch { drawerState.close() } },
                        onMenuItemClick = { materialType ->
                            scope.launch { drawerState.close() }
                            navController.navigate("material_list/$subjectId/$subjectName/${materialType.name}?isDarkMode=$isDarkMode")
                        },
                        onLogoutClick = {
                            authViewModel.signout()
                        }
                    )
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Column {
                                Text(
                                    text = subjectName ?: "Subject",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = subjectCode ?: "",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                            }
                        },
                        actions = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, "Menu")
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Study Materials",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(24.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Select a material type from the menu to view available resources.",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                                lineHeight = 22.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SubjectDrawerContent(
    onCloseClick: () -> Unit,
    onMenuItemClick: (MaterialType) -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Menu", fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            IconButton(onClick = onCloseClick) {
                Icon(Icons.Default.Close, "Close")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Section
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Person, null, tint = MaterialTheme.colorScheme.onPrimary)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Student Name", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    Text("bagiamaryam8@gmail.com", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f))
                    Text("EN: 246150307008", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f))
                    Text("Sem: 3", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        Spacer(modifier = Modifier.height(16.dp))

        // Menu Items
        val menuItems = listOf(
            Triple(MaterialType.SYLLABUS, Icons.Default.DateRange, "Syllabus"),
            Triple(MaterialType.LAB_MANUAL, Icons.Default.Build, "Lab Manual"),
            Triple(MaterialType.NOTES, Icons.Default.Menu, "Notes"),
            Triple(MaterialType.ASSIGNMENT, Icons.Default.DateRange, "Assignment"),
            Triple(MaterialType.PREVIOUS_PAPERS, Icons.Default.CheckCircle, "Previous Papers"),
            Triple(MaterialType.VIDEOS, Icons.Default.PlayArrow, "Videos"),
            Triple(MaterialType.OTHERS, Icons.Default.Star, "Others")
        )

        menuItems.forEach { (type, icon, label) ->
            DrawerItem(icon, label) { onMenuItemClick(type) }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        // Logout
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLogoutClick() }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.ExitToApp, null, tint = Color.Red)
            Spacer(modifier = Modifier.width(16.dp))
            Text("Logout", color = Color.Red, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun DrawerItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(24.dp))
        Text(text = label, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}
