package com.example.hackathonproject.ui.subject

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hackathonproject.data.model.Material
import com.example.hackathonproject.data.model.MaterialType
import com.example.hackathonproject.ui.theme.HackathonProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialListScreen(
    navController: NavController,
    subjectId: String?,
    subjectName: String?,
    materialTypeName: String?,
    isDarkMode: Boolean = false
) {
    val materialType = try {
        MaterialType.valueOf(materialTypeName ?: "")
    } catch (e: Exception) {
        MaterialType.OTHERS
    }

    // Dummy data for materials
    val materials = listOf(
        Material("1", "${materialType.displayName.lowercase()}_unit_1.pdf", "2.4 MB", materialType, ""),
        Material("2", "${materialType.displayName.lowercase()}_unit_2.pdf", "3.1 MB", materialType, ""),
        Material("3", "${materialType.displayName.lowercase()}_unit_3.pdf", "1.8 MB", materialType, ""),
        Material("4", "${materialType.displayName.lowercase()}_reference.pdf", "4.2 MB", materialType, "")
    )

    HackathonProjectTheme(darkTheme = isDarkMode) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = materialType.displayName,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = subjectName ?: "",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                        }
                    }
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(materials) { material ->
                    MaterialItem(material)
                }
            }
        }
    }
}

@Composable
fun MaterialItem(material: Material) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = material.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = material.size,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = { /* Handle Download */ }) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Download",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
