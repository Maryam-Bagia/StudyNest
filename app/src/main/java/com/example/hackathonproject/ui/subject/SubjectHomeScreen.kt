package com.example.hackathonproject.ui.subject

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hackathonproject.data.model.MaterialType
import com.example.hackathonproject.ui.theme.HackathonProjectTheme
import com.example.hackathonproject.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import com.example.hackathonproject.R

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
                        },
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
    onLogoutClick: () -> Unit,
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

        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        Spacer(modifier = Modifier.height(16.dp))

        // Menu Items
        val menuItems = listOf(
            Triple(MaterialType.SYLLABUS, R.drawable.outline_book_ribbon_24, "Syllabus"),
            Triple(MaterialType.LAB_MANUAL, R.drawable.outline_experiment_24, "Lab Manual"),
            Triple(MaterialType.NOTES, R.drawable.outline_two_pager_24, "Notes"),
            Triple(MaterialType.ASSIGNMENT, R.drawable.baseline_assignment_24, "Assignment"),
            Triple(MaterialType.PREVIOUS_PAPERS, R.drawable.outline_question_mark_24, "Previous Papers"),
            Triple(MaterialType.VIDEOS, R.drawable.outline_videocam_24, "Videos"),
            Triple(MaterialType.OTHERS, R.drawable.outline_folder_copy_24, "Others")
        )

        Column(modifier = Modifier.weight(1f)) {
            menuItems.forEach { (type, iconRes, label) ->
                DrawerItem(
                    icon = painterResource(id = iconRes),
                    label = label
                ) {
                    onMenuItemClick(type)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Logout Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLogoutClick() }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = null,
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Logout", color = Color.Red, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun DrawerItem(icon: Painter, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(text = label, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}
