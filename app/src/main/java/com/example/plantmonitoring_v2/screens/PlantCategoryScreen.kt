package com.example.plantmonitoring_v2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.launch
import com.example.plantmonitoring_v2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantCategoryScreen(
    onBackClick: () -> Unit,
    onGrowthMonitoringClick: () -> Unit = {}
) {
    
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    
    val menuItems = listOf(
        MenuItem("Growth Monitoring", Icons.Default.LocalFlorist),
        MenuItem("Plant Category", Icons.Default.List),
        MenuItem("Setup Garden & Device", Icons.Default.Agriculture),
        MenuItem("Device Status", Icons.Default.DeviceHub),
        MenuItem("Device Control", Icons.Default.Tune)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Plant Monitoring",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                menuItems.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.title) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            // Handle specific menu item clicks
                            when (index) {
                                0 -> onGrowthMonitoringClick() // Growth Monitoring
                                1 -> { /* Already on Plant Category screen */ }
                                // Add other menu items as needed
                            }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                NavigationDrawerItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) },
                    label = { Text("Back") },
                    selected = false,
                    onClick = onBackClick,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "",
                            style = MaterialTheme.typography.headlineLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Plant Category",
                        style = MaterialTheme.typography.displayMedium
                    )
                }
                
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Type of Plants",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF73747D)
                        )
                        
                        Button(
                            onClick = { /* Handle setup click */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            )
                        ) {
                            Text("Setup")
                        }
                    }
                }
                
                item {
                    var plantCategories by remember { mutableStateOf(listOf("Fruits", "Vegies", "Herbs")) }
                    var customPlantName by remember { mutableStateOf("") }
                    var showAddDialog by remember { mutableStateOf(false) }
                    
                    Column {
                        // Two-row horizontally scrollable cards
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(horizontal = 0.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Create multiple grid items for horizontal scrolling
                            val totalCards = plantCategories.size + 1 // +1 for Add Custom
                            val cardsPerGrid = 4 // 2x2 grid
                            val numberOfGrids = (totalCards + cardsPerGrid - 1) / cardsPerGrid // Ceiling division
                            
                            repeat(numberOfGrids) { gridIndex ->
                                item {
                                    LazyVerticalGrid(
                                        columns = GridCells.Fixed(2),
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(16.dp),
                                        modifier = Modifier
                                            .width(400.dp) // Fixed width for grid
                                            .height(280.dp) // Fixed height for 2 rows
                                    ) {
                                        val startIndex = gridIndex * cardsPerGrid
                                        val endIndex = minOf(startIndex + cardsPerGrid, totalCards)
                                        
                                        for (i in startIndex until endIndex) {
                                            if (i < plantCategories.size) {
                                                // Plant category card
                                                item {
                                                    Card(
                                                        modifier = Modifier
                                                            .size(120.dp),
                                                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                                        onClick = { /* Handle category click */ }
                                                    ) {
                                                        Column(
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .padding(16.dp),
                                                            horizontalAlignment = Alignment.CenterHorizontally,
                                                            verticalArrangement = Arrangement.Center
                                                        ) {
                                                            // Placeholder for plant image
                                                            Box(
                                                                modifier = Modifier
                                                                    .size(48.dp)
                                                                    .background(
                                                                        color = Color(0xFFE8F5E8),
                                                                        shape = CircleShape
                                                                    ),
                                                                contentAlignment = Alignment.Center
                                                            ) {
                                                                Icon(
                                                                    imageVector = Icons.Default.LocalFlorist,
                                                                    contentDescription = null,
                                                                    tint = Color(0xFF4CAF50),
                                                                    modifier = Modifier.size(24.dp)
                                                                )
                                                            }
                                                            
                                                            Spacer(modifier = Modifier.height(8.dp))
                                                            
                                                            Text(
                                                                text = plantCategories[i],
                                                                style = MaterialTheme.typography.bodyMedium,
                                                                textAlign = TextAlign.Center
                                                            )
                                                        }
                                                    }
                                                }
                                            } else {
                                                // Add Custom card (always at the end)
                                                item {
                                                    Card(
                                                        modifier = Modifier
                                                            .size(120.dp),
                                                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                                        onClick = { showAddDialog = true }
                                                    ) {
                                                        Column(
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .padding(16.dp),
                                                            horizontalAlignment = Alignment.CenterHorizontally,
                                                            verticalArrangement = Arrangement.Center
                                                        ) {
                                                            // Add icon
                                                            Box(
                                                                modifier = Modifier
                                                                    .size(48.dp)
                                                                    .background(
                                                                        color = Color(0xFFF0F0F0),
                                                                        shape = CircleShape
                                                                    ),
                                                                contentAlignment = Alignment.Center
                                                            ) {
                                                                Icon(
                                                                    imageVector = Icons.Default.Add,
                                                                    contentDescription = null,
                                                                    tint = Color(0xFF666666),
                                                                    modifier = Modifier.size(24.dp)
                                                                )
                                                            }
                                                            
                                                            Spacer(modifier = Modifier.height(8.dp))
                                                            
                                                            Text(
                                                                text = "Add Custom",
                                                                style = MaterialTheme.typography.bodyMedium,
                                                                textAlign = TextAlign.Center
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        // Add Custom Plant Dialog
                        if (showAddDialog) {
                            AlertDialog(
                                onDismissRequest = { showAddDialog = false },
                                title = { Text("Add Custom Plant Category") },
                                text = {
                                    TextField(
                                        value = customPlantName,
                                        onValueChange = { customPlantName = it },
                                        label = { Text("Plant Category Name") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            if (customPlantName.isNotBlank()) {
                                                plantCategories = plantCategories + customPlantName
                                                customPlantName = ""
                                                showAddDialog = false
                                            }
                                        }
                                    ) {
                                        Text("Add")
                                    }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = {
                                            customPlantName = ""
                                            showAddDialog = false
                                        }
                                    ) {
                                        Text("Cancel")
                                    }
                                }
                            )
                        }
                    }
                }
                
                item {
                    // My Plant section
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "My Plant",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF73747D)
                        )
                        
                        Button(
                            onClick = { /* Handle see all click */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "See All",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF73747D)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                                tint = Color(0xFF73747D),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
                
                item {
                    // Action buttons row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { /* Handle plant action */ },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFlorist,
                                contentDescription = "Plant",
                                tint = Color(0xFF73747D),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        
                        IconButton(
                            onClick = { /* Handle delete action */ },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color(0xFF73747D),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        
                        IconButton(
                            onClick = { /* Handle more options */ },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More options",
                                tint = Color(0xFF73747D),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
                
                item {
                    // Plant Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF2F2F2)
                        ),
                        onClick = { /* Handle plant click */ }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Plant Image
                            Image(
                                painter = painterResource(id = R.drawable.plant_list),
                                contentDescription = "Tomato Plant",
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            // Plant Info
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Tomato",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                
                                Spacer(modifier = Modifier.height(4.dp))
                                
                                Text(
                                    text = "Fruit",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            // Right side info
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = "15d",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
} 