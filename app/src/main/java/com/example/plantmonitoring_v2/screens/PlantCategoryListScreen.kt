package com.example.plantmonitoring_v2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.plantmonitoring_v2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantCategoryListScreen(
    categoryName: String,
    onBackClick: () -> Unit,
    onPlantClick: (PlantItem) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = categoryName,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    TextButton(
                        onClick = onBackClick,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ChevronLeft,
                                contentDescription = "Back"
                            )
                            Text(
                                text = "Back",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* Handle weather click */ }
                    ) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = "Weather"
                        )
                    }
                }
            )
        },
        floatingActionButton = { /* Removed FAB */ }
    ) { paddingValues ->
        // Only one scrollable container!
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Centered TopBar Title (already handled by TopAppBar, but ensure Column is centered)
            var searchQuery by remember { mutableStateOf("") }
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search plants...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color(0xFFF2F2F2),
                    unfocusedContainerColor = Color(0xFFF2F2F2)
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { /* Handle app interface click */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Apps,
                        contentDescription = "App Interface"
                    )
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Sample plant data - you can replace this with actual data
                val plants = when (categoryName) {
                    "Fruits" -> listOf(
                        PlantItem("Apple", "Malus domestica", "Ready to harvest", "200ml"),
                        PlantItem("Orange", "Citrus sinensis", "Growing", "150ml"),
                        PlantItem("Banana", "Musa acuminata", "Flowering", "300ml")
                    )
                    "Vegies" -> listOf(
                        PlantItem("Tomato", "Solanum lycopersicum", "Fruiting", "180ml"),
                        PlantItem("Carrot", "Daucus carota", "Growing", "120ml"),
                        PlantItem("Lettuce", "Lactuca sativa", "Ready to harvest", "100ml")
                    )
                    "Herbs" -> listOf(
                        PlantItem("Basil", "Ocimum basilicum", "Growing", "80ml"),
                        PlantItem("Mint", "Mentha spicata", "Ready to harvest", "90ml"),
                        PlantItem("Rosemary", "Salvia rosmarinus", "Flowering", "110ml")
                    )
                    else -> listOf(
                        PlantItem("Sample Plant", "Sample Species", "Growing", "150ml")
                    )
                }

                items(plants) { plant ->
                    PlantCard(
                        plant = plant,
                        onClick = { onPlantClick(plant) }
                    )
                }
            }
        }
    }
}

@Composable
fun PlantCard(
    plant: PlantItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Information icon in top-right corner
            IconButton(
                onClick = { /* Handle info click */ },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Plant information",
                    tint = Color(0xFF73747D),
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Plant Image Section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(
                            color = Color(0xFFF5F5F5),
                            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rosemary),
                        contentDescription = plant.name,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                // Bottom Container with Text
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                        )
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = plant.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Status indicator
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(
                                            color = when (plant.status) {
                                                "Ready to harvest" -> Color(0xFF4CAF50)
                                                "Growing" -> Color(0xFFFF9800)
                                                "Flowering" -> Color(0xFF9C27B0)
                                                "Fruiting" -> Color(0xFFE91E63)
                                                else -> Color(0xFF9E9E9E)
                                            },
                                            shape = CircleShape
                                        )
                                )
                                
                                Spacer(modifier = Modifier.width(4.dp))
                                
                                Text(
                                    text = plant.waterAmount,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        
                        Text(
                            text = "See more",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

data class PlantItem(
    val name: String,
    val species: String,
    val status: String,
    val waterAmount: String
    )