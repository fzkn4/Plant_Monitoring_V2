package com.example.plantmonitoring_v2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.plantmonitoring_v2.R

data class Plant(
    val id: Int,
    val name: String,
    val type: String,
    val daysPlanted: Int,
    val imageRes: Int,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantListScreen(
    onBackClick: () -> Unit
) {
    val plants = remember {
        listOf(
            Plant(1, "Tomato", "Fruit", 15, R.drawable.plant_list),
            Plant(2, "Basil", "Herb", 30, R.drawable.plant_list),
            Plant(3, "Lettuce", "Vegetable", 20, R.drawable.plant_list),
            Plant(4, "Strawberry", "Fruit", 25, R.drawable.plant_list),
            Plant(5, "Mint", "Herb", 12, R.drawable.plant_list),
            Plant(6, "Carrot", "Vegetable", 18, R.drawable.plant_list),
            Plant(7, "Pepper", "Fruit", 22, R.drawable.plant_list),
            Plant(8, "Rosemary", "Herb", 35, R.drawable.plant_list)
        )
    }

    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }

    val filteredPlants = plants.filter { plant ->
        val matchesSearch = plant.name.contains(searchQuery, ignoreCase = true) ||
                plant.type.contains(searchQuery, ignoreCase = true)
        val matchesFilter = selectedFilter == "All" || plant.type == selectedFilter
        matchesSearch && matchesFilter
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Plant List",
                        style = MaterialTheme.typography.headlineMedium
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
                                text = "Back to Home",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle add plant */ }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Plant"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
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

            Spacer(modifier = Modifier.height(16.dp))

            // Filter Chips
            FilterChipsRow(
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Plant List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredPlants) { plant ->
                    PlantCard(plant = plant)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantCard(
    plant: Plant,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
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
                painter = painterResource(id = plant.imageRes),
                contentDescription = plant.name,
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
                    text = plant.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = plant.type,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Right side info
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${plant.daysPlanted}d",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun FilterChipsRow(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        val filters = listOf("All", "Fruit", "Vegetable", "Herb")
        items(filters) { filter ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
                label = { Text(filter) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFF2F2F2),
                    containerColor = Color(0xFFF2F2F2),
                    selectedLabelColor = MaterialTheme.colorScheme.onSurface,
                    labelColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
} 