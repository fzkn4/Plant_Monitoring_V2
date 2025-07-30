package com.example.plantmonitoring_v2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*
import com.example.plantmonitoring_v2.ui.theme.headlineRegular
import androidx.compose.ui.graphics.Color
import com.example.plantmonitoring_v2.R

data class ScheduledTask(
    val plantName: String,
    val plantId: String,
    val days: Int,
    val waterPerDay: String,
    val wateringSchedules: List<WateringSchedule>
)

data class WateringSchedule(
    val time: String,
    val amount: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnFieldScreen(
    onBackClick: () -> Unit,
    onPlantClick: (ScheduledTask) -> Unit
) {
    // Current time state
    var currentTime by remember { mutableStateOf("") }
    
    // Search state
    var searchQuery by remember { mutableStateOf("") }
    
    // Scheduled tasks data
    val scheduledTasks = remember {
        listOf(
            ScheduledTask(
                plantName = "Tomato",
                plantId = "T001",
                days = 45,
                waterPerDay = "1.2L",
                wateringSchedules = listOf(
                    WateringSchedule("08:00", "1.2L"),
                    WateringSchedule("14:00", "0.8L")
                )
            ),
            ScheduledTask(
                plantName = "Basil",
                plantId = "B002",
                days = 30,
                waterPerDay = "0.8L",
                wateringSchedules = listOf(
                    WateringSchedule("09:30", "0.8L")
                )
            ),
            ScheduledTask(
                plantName = "Lettuce",
                plantId = "L003",
                days = 25,
                waterPerDay = "1.0L",
                wateringSchedules = listOf(
                    WateringSchedule("07:15", "1.0L")
                )
            ),
            ScheduledTask(
                plantName = "Pepper",
                plantId = "P004",
                days = 40,
                waterPerDay = "1.5L",
                wateringSchedules = listOf(
                    WateringSchedule("06:00", "0.8L"),
                    WateringSchedule("12:00", "0.5L"),
                    WateringSchedule("18:00", "0.2L")
                )
            )
        )
    }
    
    // Update time every second
    LaunchedEffect(Unit) {
        while (true) {
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            currentTime = timeFormat.format(Date())
            kotlinx.coroutines.delay(1000)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "On Monitoring",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
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
                                contentDescription = "Back",
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Back to Home",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                },
                actions = {
                    // Weather and Time display
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        // Weather
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.WbSunny,
                                contentDescription = "Weather",
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Sunny",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        
                        // Time
                        Text(
                            text = currentTime,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                // Batch Name, plants count, and View Field button row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left side: Batch Name and plants count
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Batch Name",
                            style = headlineRegular
                        )
                        Text(
                            text = "12 plants",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    
                    // Right side: View Field button
                    Button(
                        onClick = { /* Handle view field click */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF38C070)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("View Field")
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
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
                
                // Water Metrics Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF2F2F2)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Information icon in top-right corner
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Information",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(20.dp),
                            tint = Color(0xFF73747D)
                        )
                        
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Water Metrics",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Water metrics rows
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Left column: Total Watering (emphasized)
                                Column(
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.WaterDrop,
                                            contentDescription = "Water Drop",
                                            modifier = Modifier.size(16.dp),
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = "Total Watering",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Text(
                                        text = "25.5L",
                                        style = MaterialTheme.typography.displayMedium,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                    )
                                }
                                
                                // Right column: Today's metrics grouped together
                                Column(
                                    horizontalAlignment = Alignment.End
                                ) {
                                    // Water Allocated Today
                                    Text(
                                        text = "Water Allocated Today",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "3.2L",
                                        style = MaterialTheme.typography.displayMedium,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                    )
                                    
                                    Spacer(modifier = Modifier.height(8.dp))
                                    
                                    // Water Consumption Today
                                    Text(
                                        text = "Water Consumption Today",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "2.8L",
                                        style = MaterialTheme.typography.displayMedium,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Growth Progress Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF2F2F2)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Growth Progress",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Progress bar container
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .background(
                                    color = Color(0xFFE0E0E0),
                                    shape = RoundedCornerShape(4.dp)
                                )
                        ) {
                            // Progress bar (green)
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.65f) // 65% progress
                                    .background(
                                        color = Color(0xFF00B050),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Timeline labels
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Nursery",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Harvest",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Current progress indicator
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Current days
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Current Days",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "45",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                )
                            }
                            
                            // Growth status
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = "Growth",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Vegetative",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Scheduled Tasks Section
                Text(
                    text = "Scheduled Tasks",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                // Scheduled Tasks Cards
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(scheduledTasks) { task ->
                        ScheduledTaskCard(
                            task = task,
                            onClick = { onPlantClick(task) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduledTaskCard(
    task: ScheduledTask,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF2F2F2)
        ),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left column: Plant image
            Image(
                painter = painterResource(id = R.drawable.rosemary),
                contentDescription = task.plantName,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Middle column: Text content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Plant name and ID row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = task.plantName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                    Text(
                        text = "ID: ${task.plantId}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Days and water per day row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Days",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${task.days}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Water per Day",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = task.waterPerDay,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Scheduled watering section
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Scheduled Watering",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // Display watering schedules
                    task.wateringSchedules.forEachIndexed { index, schedule ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = schedule.time,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                            Text(
                                text = schedule.amount,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                        }
                        
                        // Add spacing between multiple schedules (except for the last one)
                        if (index < task.wateringSchedules.size - 1) {
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Right column: Chevron right icon
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "View details",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}