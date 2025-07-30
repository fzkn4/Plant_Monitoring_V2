package com.example.plantmonitoring_v2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import android.widget.Toast
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.example.plantmonitoring_v2.R
import com.example.plantmonitoring_v2.ui.theme.headlineRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantDetailScreen(
    plant: ScheduledTask,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "",
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
                                text = "Back",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
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
            // Plant Image and Basic Info
            item {
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
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.rosemary),
                            contentDescription = plant.plantName,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = plant.plantName,
                            style = headlineRegular,
                            textAlign = TextAlign.Center
                        )
                        
                        Text(
                            text = "ID: ${plant.plantId}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Growth stage indicator
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(
                                        color = Color(0xFF00B050),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                            Text(
                                text = "Vegetative Stage",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                        }
                    }
                }
            }
            
            // Growth Progress
            item {
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
                        
                        // Progress bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .background(
                                    color = Color(0xFFE0E0E0),
                                    shape = RoundedCornerShape(6.dp)
                                )
                        ) {
                            val progress = (plant.days / 70.0).coerceIn(0.0, 1.0) // Assuming 70 days total
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(progress.toFloat())
                                    .background(
                                        color = Color(0xFF00B050),
                                        shape = RoundedCornerShape(6.dp)
                                    )
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Day ${plant.days}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                            Text(
                                text = "70 days",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            
            // Water Usage Overview
            item {
                val context = LocalContext.current
                
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
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.WaterDrop,
                                        contentDescription = "Water",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "Water Usage Overview",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                    )
                                }
                                
                                // Info button
                                IconButton(
                                    onClick = { 
                                        Toast.makeText(context, "Press bars for detailed breakdown", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = "Information",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Water usage summary
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = "Today's Total",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "2.8L",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                
                                Column(
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Text(
                                        text = "Scheduled",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = plant.waterPerDay,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Water usage graph
                            Text(
                                text = "Water Usage History (Last 7 Days)",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.`Bold`
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            // Water usage graph
                            WaterUsageGraph(
                                waterData = listOf(
                                    WaterDataPoint("Mon", 2.1f, 1.8f, 0.3f),
                                    WaterDataPoint("Tue", 2.8f, 1.8f, 1.0f),
                                    WaterDataPoint("Wed", 1.9f, 1.8f, 0.1f),
                                    WaterDataPoint("Thu", 3.2f, 1.8f, 1.4f),
                                    WaterDataPoint("Fri", 2.5f, 1.8f, 0.7f),
                                    WaterDataPoint("Sat", 2.0f, 1.8f, 0.2f),
                                    WaterDataPoint("Sun", 2.8f, 1.8f, 1.0f)
                                )
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Legend
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(
                                                color = Color(0xFF38C070),
                                                shape = RoundedCornerShape(4.dp)
                                            )
                                    )
                                    Text(
                                        text = "Scheduled",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(
                                                color = Color(0xFFFF6B35),
                                                shape = RoundedCornerShape(4.dp)
                                            )
                                    )
                                    Text(
                                        text = "Emergency",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            // Environmental Preferences - New Design
            item {
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
                            text = "Environmental Preferences",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                                                 // Temperature Section
                         Column(
                             modifier = Modifier.fillMaxWidth()
                         ) {
                             // Current Temperature Indicator at Top
                             Row(
                                 modifier = Modifier.fillMaxWidth(),
                                 horizontalArrangement = Arrangement.Center,
                                 verticalAlignment = Alignment.CenterVertically
                             ) {
                                 Icon(
                                     imageVector = Icons.Default.Thermostat,
                                     contentDescription = "Temperature",
                                     tint = Color(0xFF4CAF50),
                                     modifier = Modifier.size(20.dp)
                                 )
                                 Spacer(modifier = Modifier.width(8.dp))
                                 Text(
                                     text = "24°C",
                                     style = MaterialTheme.typography.titleLarge,
                                     fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                     color = Color(0xFF4CAF50)
                                 )
                                 Spacer(modifier = Modifier.width(8.dp))
                                 Text(
                                     text = "Current",
                                     style = MaterialTheme.typography.bodyMedium,
                                     color = MaterialTheme.colorScheme.onSurfaceVariant
                                 )
                             }
                             
                             Spacer(modifier = Modifier.height(12.dp))
                             
                             // Temperature Range Label
                             Row(
                                 modifier = Modifier.fillMaxWidth(),
                                 horizontalArrangement = Arrangement.Center
                             ) {
                                 Text(
                                     text = "Optimal Range: 20-25°C",
                                     style = MaterialTheme.typography.bodyMedium,
                                     fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                     color = Color(0xFF4CAF50)
                                 )
                             }
                             
                             Spacer(modifier = Modifier.height(8.dp))
                             
                             // Temperature Spectrum Bar - FULL WIDTH
                             Box(
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .height(20.dp)
                                     .background(
                                         color = Color(0xFFE0E0E0),
                                         shape = RoundedCornerShape(4.dp)
                                     )
                             ) {
                                 // Red block (unsuitable)
                                 Box(
                                     modifier = Modifier
                                         .fillMaxHeight()
                                         .fillMaxWidth(0.2f)
                                         .background(
                                             color = Color(0xFFFF4444),
                                             shape = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)
                                         )
                                 )
                                 
                                 // Orange block (tolerable)
                                 Box(
                                     modifier = Modifier
                                         .fillMaxHeight()
                                         .fillMaxWidth(0.15f)
                                         .offset(x = (0.2f * 100).dp)
                                         .background(
                                             color = Color(0xFFFF8C00)
                                         )
                                 )
                                 
                                 // Green block (optimal) - BIGGER for emphasis
                                 Box(
                                     modifier = Modifier
                                         .fillMaxHeight()
                                         .fillMaxWidth(0.3f)
                                         .offset(x = (0.35f * 100).dp)
                                         .background(
                                             color = Color(0xFF4CAF50)
                                         )
                                 )
                                 
                                 // Orange block (tolerable)
                                 Box(
                                     modifier = Modifier
                                         .fillMaxHeight()
                                         .fillMaxWidth(0.15f)
                                         .offset(x = (0.65f * 100).dp)
                                         .background(
                                             color = Color(0xFFFF8C00)
                                         )
                                 )
                                 
                                 // Red block (unsuitable)
                                 Box(
                                     modifier = Modifier
                                         .fillMaxHeight()
                                         .fillMaxWidth(0.2f)
                                         .offset(x = (0.8f * 100).dp)
                                         .background(
                                             color = Color(0xFFFF4444),
                                             shape = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)
                                         )
                                 )
                                 
                                 // Current temperature indicator
                                 val currentTemp = 24f
                                 val tempPosition = ((currentTemp - 10) / 30).coerceIn(0f, 1f)
                                 Box(
                                     modifier = Modifier
                                         .size(8.dp)
                                         .offset(x = (tempPosition * 100).dp)
                                         .background(
                                             color = Color.White,
                                             shape = RoundedCornerShape(4.dp)
                                         )
                                         .align(Alignment.CenterStart)
                                 )
                             }
                         }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                                                 // Humidity Section
                         Column(
                             modifier = Modifier.fillMaxWidth()
                         ) {
                             // Current Humidity Indicator at Top
                             Row(
                                 modifier = Modifier.fillMaxWidth(),
                                 horizontalArrangement = Arrangement.Center,
                                 verticalAlignment = Alignment.CenterVertically
                             ) {
                                 Icon(
                                     imageVector = Icons.Default.WaterDrop,
                                     contentDescription = "Humidity",
                                     tint = Color(0xFF4CAF50),
                                     modifier = Modifier.size(20.dp)
                                 )
                                 Spacer(modifier = Modifier.width(8.dp))
                                 Text(
                                     text = "65%",
                                     style = MaterialTheme.typography.titleLarge,
                                     fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                     color = Color(0xFF4CAF50)
                                 )
                                 Spacer(modifier = Modifier.width(8.dp))
                                 Text(
                                     text = "Current",
                                     style = MaterialTheme.typography.bodyMedium,
                                     color = MaterialTheme.colorScheme.onSurfaceVariant
                                 )
                             }
                             
                             Spacer(modifier = Modifier.height(12.dp))
                             
                             // Humidity Range Label
                             Row(
                                 modifier = Modifier.fillMaxWidth(),
                                 horizontalArrangement = Arrangement.Center
                             ) {
                                 Text(
                                     text = "Optimal Range: 50-70%",
                                     style = MaterialTheme.typography.bodyMedium,
                                     fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                     color = Color(0xFF4CAF50)
                                 )
                             }
                             
                             Spacer(modifier = Modifier.height(8.dp))
                             
                             // Humidity Spectrum Bar - FULL WIDTH
                             Box(
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .height(20.dp)
                                     .background(
                                         color = Color(0xFFE0E0E0),
                                         shape = RoundedCornerShape(4.dp)
                                     )
                             ) {
                                 // Red block (low)
                                 Box(
                                     modifier = Modifier
                                         .fillMaxHeight()
                                         .fillMaxWidth(0.2f)
                                         .background(
                                             color = Color(0xFFFF4444),
                                             shape = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)
                                         )
                                 )
                                 
                                 // Orange block (tolerable)
                                 Box(
                                     modifier = Modifier
                                         .fillMaxHeight()
                                         .fillMaxWidth(0.15f)
                                         .offset(x = (0.2f * 100).dp)
                                         .background(
                                             color = Color(0xFFFF8C00)
                                         )
                                 )
                                 
                                 // Green block (optimal) - BIGGER for emphasis
                                 Box(
                                     modifier = Modifier
                                         .fillMaxHeight()
                                         .fillMaxWidth(0.3f)
                                         .offset(x = (0.35f * 100).dp)
                                         .background(
                                             color = Color(0xFF4CAF50)
                                         )
                                 )
                                 
                                 // Orange block (tolerable)
                                 Box(
                                     modifier = Modifier
                                         .fillMaxHeight()
                                         .fillMaxWidth(0.15f)
                                         .offset(x = (0.65f * 100).dp)
                                         .background(
                                             color = Color(0xFFFF8C00)
                                         )
                                 )
                                 
                                 // Red block (high)
                                 Box(
                                     modifier = Modifier
                                         .fillMaxHeight()
                                         .fillMaxWidth(0.2f)
                                         .offset(x = (0.8f * 100).dp)
                                         .background(
                                             color = Color(0xFFFF4444),
                                             shape = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)
                                         )
                                 )
                                 
                                 // Current humidity indicator
                                 val currentHumidity = 65f
                                 val humidityPosition = (currentHumidity / 100).coerceIn(0f, 1f)
                                 Box(
                                     modifier = Modifier
                                         .size(8.dp)
                                         .offset(x = (humidityPosition * 100).dp)
                                         .background(
                                             color = Color.White,
                                             shape = RoundedCornerShape(4.dp)
                                         )
                                         .align(Alignment.CenterStart)
                                 )
                             }
                         }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Status Indicator
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(
                                        color = Color(0xFF4CAF50),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Conditions Optimal",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                color = Color(0xFF4CAF50)
                            )
                                                 }
                     }
                 }
             }
             
             // Watering and Maintenance
             item {
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
                             text = "Watering and Maintenance",
                             style = MaterialTheme.typography.titleMedium,
                             fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                         )
                         
                         Spacer(modifier = Modifier.height(16.dp))
                         
                         // High Temperature Exposure Watering
                         Row(
                             modifier = Modifier.fillMaxWidth(),
                             horizontalArrangement = Arrangement.SpaceBetween,
                             verticalAlignment = Alignment.CenterVertically
                         ) {
                             Column(
                                 horizontalAlignment = Alignment.Start
                             ) {
                                 Text(
                                     text = "High Temperature Exposure",
                                     style = MaterialTheme.typography.bodyMedium,
                                     fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                 )
                                 Text(
                                     text = "Additional watering when temp > 25°C",
                                     style = MaterialTheme.typography.bodySmall,
                                     color = MaterialTheme.colorScheme.onSurfaceVariant
                                 )
                             }
                             
                             Row(
                                 verticalAlignment = Alignment.CenterVertically,
                                 horizontalArrangement = Arrangement.spacedBy(8.dp)
                             ) {
                                 Icon(
                                     imageVector = Icons.Default.WaterDrop,
                                     contentDescription = "Water",
                                     tint = Color(0xFFFF6B35),
                                     modifier = Modifier.size(20.dp)
                                 )
                                 Text(
                                     text = "+0.5L",
                                     style = MaterialTheme.typography.titleMedium,
                                     fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                     color = Color(0xFFFF6B35)
                                 )
                             }
                         }
                         
                         Spacer(modifier = Modifier.height(12.dp))
                         
                         // Low Humidity Exposure Watering
                         Row(
                             modifier = Modifier.fillMaxWidth(),
                             horizontalArrangement = Arrangement.SpaceBetween,
                             verticalAlignment = Alignment.CenterVertically
                         ) {
                             Column(
                                 horizontalAlignment = Alignment.Start
                             ) {
                                 Text(
                                     text = "Low Humidity Exposure",
                                     style = MaterialTheme.typography.bodyMedium,
                                     fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                 )
                                 Text(
                                     text = "Additional watering when humidity < 50%",
                                     style = MaterialTheme.typography.bodySmall,
                                     color = MaterialTheme.colorScheme.onSurfaceVariant
                                 )
                             }
                             
                             Row(
                                 verticalAlignment = Alignment.CenterVertically,
                                 horizontalArrangement = Arrangement.spacedBy(8.dp)
                             ) {
                                 Icon(
                                     imageVector = Icons.Default.WaterDrop,
                                     contentDescription = "Water",
                                     tint = Color(0xFFFF6B35),
                                     modifier = Modifier.size(20.dp)
                                 )
                                 Text(
                                     text = "+0.3L",
                                     style = MaterialTheme.typography.titleMedium,
                                     fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                     color = Color(0xFFFF6B35)
                                 )
                             }
                         }
                         
                         
                     }
                 }
             }
             
                                                       // Watering Schedules
             item {
                 var showAddDialog by remember { mutableStateOf(false) }
                 var showEditDialog by remember { mutableStateOf<WateringSchedule?>(null) }
                 var schedules by remember { mutableStateOf(plant.wateringSchedules.toMutableList()) }
                 
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
                         Row(
                             modifier = Modifier.fillMaxWidth(),
                             horizontalArrangement = Arrangement.SpaceBetween,
                             verticalAlignment = Alignment.CenterVertically
                         ) {
                             Text(
                                 text = "Watering Schedules",
                                 style = MaterialTheme.typography.titleMedium,
                                 fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                             )
                             
                             // Add button
                             IconButton(
                                 onClick = { showAddDialog = true },
                                 modifier = Modifier
                                     .size(32.dp)
                                     .background(
                                         color = Color(0xFF38C070),
                                         shape = RoundedCornerShape(8.dp)
                                     )
                             ) {
                                 Icon(
                                     imageVector = Icons.Default.Add,
                                     contentDescription = "Add Schedule",
                                     tint = Color.White,
                                     modifier = Modifier.size(16.dp)
                                 )
                             }
                         }
                         
                         Spacer(modifier = Modifier.height(16.dp))
                         
                         // List of watering schedules
                         schedules.forEachIndexed { index, schedule ->
                             Row(
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .padding(vertical = 8.dp),
                                 horizontalArrangement = Arrangement.SpaceBetween,
                                 verticalAlignment = Alignment.CenterVertically
                             ) {
                                 Row(
                                     verticalAlignment = Alignment.CenterVertically,
                                     horizontalArrangement = Arrangement.spacedBy(12.dp)
                                 ) {
                                     Icon(
                                         imageVector = Icons.Default.Schedule,
                                         contentDescription = "Schedule",
                                         tint = MaterialTheme.colorScheme.primary,
                                         modifier = Modifier.size(20.dp)
                                     )
                                     Column(
                                         horizontalAlignment = Alignment.Start
                                     ) {
                                         Text(
                                             text = schedule.time,
                                             style = MaterialTheme.typography.bodyMedium,
                                             fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                         )
                                         Text(
                                             text = "Daily Schedule",
                                             style = MaterialTheme.typography.bodySmall,
                                             color = MaterialTheme.colorScheme.onSurfaceVariant
                                         )
                                     }
                                 }
                                 
                                 Row(
                                     verticalAlignment = Alignment.CenterVertically,
                                     horizontalArrangement = Arrangement.spacedBy(8.dp)
                                 ) {
                                     Icon(
                                         imageVector = Icons.Default.WaterDrop,
                                         contentDescription = "Water",
                                         tint = Color(0xFF38C070),
                                         modifier = Modifier.size(20.dp)
                                     )
                                     Text(
                                         text = "${schedule.amount}L",
                                         style = MaterialTheme.typography.titleMedium,
                                         fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                         color = Color(0xFF38C070)
                                     )
                                     
                                     Spacer(modifier = Modifier.width(8.dp))
                                     
                                     // Edit button
                                     IconButton(
                                         onClick = { showEditDialog = schedule },
                                         modifier = Modifier.size(24.dp)
                                     ) {
                                         Icon(
                                             imageVector = Icons.Default.Edit,
                                             contentDescription = "Edit",
                                             tint = MaterialTheme.colorScheme.primary,
                                             modifier = Modifier.size(16.dp)
                                         )
                                     }
                                     
                                     // Delete button
                                     IconButton(
                                         onClick = { 
                                             schedules = schedules.toMutableList().apply { removeAt(index) }
                                         },
                                         modifier = Modifier.size(24.dp)
                                     ) {
                                         Icon(
                                             imageVector = Icons.Default.Delete,
                                             contentDescription = "Delete",
                                             tint = Color(0xFFFF4444),
                                             modifier = Modifier.size(16.dp)
                                         )
                                     }
                                 }
                             }
                             
                             if (index < schedules.size - 1) {
                                 Divider(
                                     modifier = Modifier.padding(vertical = 4.dp),
                                     color = Color(0xFFE0E0E0)
                                 )
                             }
                         }
                         
                         // Empty state
                         if (schedules.isEmpty()) {
                             Column(
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .padding(vertical = 32.dp),
                                 horizontalAlignment = Alignment.CenterHorizontally
                             ) {
                                 Icon(
                                     imageVector = Icons.Default.Schedule,
                                     contentDescription = "No Schedules",
                                     tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                     modifier = Modifier.size(48.dp)
                                 )
                                 Spacer(modifier = Modifier.height(8.dp))
                                 Text(
                                     text = "No watering schedules",
                                     style = MaterialTheme.typography.bodyMedium,
                                     color = MaterialTheme.colorScheme.onSurfaceVariant
                                 )
                                 Text(
                                     text = "Tap + to add a schedule",
                                     style = MaterialTheme.typography.bodySmall,
                                     color = MaterialTheme.colorScheme.onSurfaceVariant
                                 )
                             }
                         }
                     }
                 }
                 
                 // Add Schedule Dialog
                 if (showAddDialog) {
                     ScheduleDialog(
                         schedule = null,
                         onDismiss = { showAddDialog = false },
                         onSave = { time, amount ->
                             schedules = schedules.toMutableList().apply {
                                 add(WateringSchedule(time, amount))
                             }
                             showAddDialog = false
                         }
                     )
                 }
                 
                 // Edit Schedule Dialog
                 showEditDialog?.let { schedule ->
                     ScheduleDialog(
                         schedule = schedule,
                         onDismiss = { showEditDialog = null },
                         onSave = { time, amount ->
                             val index = schedules.indexOf(schedule)
                             if (index != -1) {
                                 schedules = schedules.toMutableList().apply {
                                     set(index, WateringSchedule(time, amount))
                                 }
                             }
                             showEditDialog = null
                         }
                     )
                                  }
             }
             
             // Planting Season Guide
             item {
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
                             text = "Planting Season Guide",
                             style = MaterialTheme.typography.titleMedium,
                             fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                         )
                         
                         Spacer(modifier = Modifier.height(16.dp))
                         
                         // Planting season graph
                         PlantingSeasonGraph()
                         
                         Spacer(modifier = Modifier.height(12.dp))
                         
                         // Legend
                         Row(
                             modifier = Modifier.fillMaxWidth(),
                             horizontalArrangement = Arrangement.SpaceEvenly
                         ) {
                             Row(
                                 verticalAlignment = Alignment.CenterVertically,
                                 horizontalArrangement = Arrangement.spacedBy(4.dp)
                             ) {
                                 Box(
                                     modifier = Modifier
                                         .size(8.dp)
                                         .background(
                                             color = Color(0xFF4CAF50),
                                             shape = RoundedCornerShape(4.dp)
                                         )
                                 )
                                                                   Text(
                                      text = "Ideal",
                                      style = MaterialTheme.typography.bodySmall
                                  )
                             }
                             
                             Row(
                                 verticalAlignment = Alignment.CenterVertically,
                                 horizontalArrangement = Arrangement.spacedBy(4.dp)
                             ) {
                                 Box(
                                     modifier = Modifier
                                         .size(8.dp)
                                         .background(
                                             color = Color(0xFFFF8C00),
                                             shape = RoundedCornerShape(4.dp)
                                         )
                                 )
                                 Text(
                                     text = "Good",
                                     style = MaterialTheme.typography.bodySmall
                                 )
                             }
                             
                             Row(
                                 verticalAlignment = Alignment.CenterVertically,
                                 horizontalArrangement = Arrangement.spacedBy(4.dp)
                             ) {
                                 Box(
                                     modifier = Modifier
                                         .size(8.dp)
                                         .background(
                                             color = Color(0xFFFF4444),
                                             shape = RoundedCornerShape(4.dp)
                                         )
                                 )
                                 Text(
                                     text = "Poor",
                                     style = MaterialTheme.typography.bodySmall
                                 )
                             }
                         }
                     }
                 }
             }
             
             
        }
    }
}

data class WaterDataPoint(
    val day: String,
    val totalWater: Float,
    val scheduledWater: Float,
    val emergencyWater: Float
)

@Composable
fun WaterUsageGraph(
    waterData: List<WaterDataPoint>
) {
    val maxWater = waterData.maxOfOrNull { it.totalWater } ?: 0f
    var selectedDataPoint by remember { mutableStateOf<WaterDataPoint?>(null) }
    var showTooltip by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Graph container with dynamic height
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp) // Increased height to accommodate labels
                .background(
                    color = Color(0xFFF8F9FA),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            // Graph bars
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                waterData.forEach { dataPoint ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.height(140.dp) // Fixed height for consistent spacing
                    ) {
                        // Stacked bar chart with press functionality
                        Box(
                            modifier = Modifier
                                .width(28.dp) // Slightly wider bars
                                .height((dataPoint.totalWater / maxWater * 60).dp) // Reduced bar height to leave space for labels
                                .background(
                                    color = Color(0xFF38C070),
                                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                )
                                .pointerInput(dataPoint) {
                                    detectTapGestures(
                                        onPress = {
                                            selectedDataPoint = dataPoint
                                            showTooltip = true
                                            tryAwaitRelease()
                                            showTooltip = false
                                        }
                                    )
                                }
                        ) {
                            // Emergency water overlay (if any)
                            if (dataPoint.emergencyWater > 0) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height((dataPoint.emergencyWater / dataPoint.totalWater * 100).dp)
                                        .background(
                                            color = Color(0xFFFF6B35),
                                            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                        )
                                        .align(Alignment.TopCenter)
                                        .pointerInput(dataPoint) {
                                            detectTapGestures(
                                                onPress = {
                                                    selectedDataPoint = dataPoint
                                                    showTooltip = true
                                                    tryAwaitRelease()
                                                    showTooltip = false
                                                }
                                            )
                                        }
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp)) // More spacing
                        
                        // Day label
                        Text(
                            text = dataPoint.day,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(2.dp))
                        
                        // Water amount label
                        Text(
                            text = "${dataPoint.totalWater}L",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            
            // Tooltip overlay
            if (showTooltip && selectedDataPoint != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .offset(y = 20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2C3E50)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${selectedDataPoint!!.day}",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            // Total
                            Text(
                                text = "Total: ${selectedDataPoint!!.totalWater}L",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                            
                            // Scheduled
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(
                                            color = Color(0xFF38C070),
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                )
                                Text(
                                    text = "Scheduled: ${selectedDataPoint!!.scheduledWater}L",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White
                                )
                            }
                            
                            // Emergency (only show if > 0)
                            if (selectedDataPoint!!.emergencyWater > 0) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(
                                                color = Color(0xFFFF6B35),
                                                shape = RoundedCornerShape(4.dp)
                                            )
                                    )
                                    Text(
                                        text = "Emergency: ${selectedDataPoint!!.emergencyWater}L",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.White
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

@Composable
fun ScheduleDialog(
    schedule: WateringSchedule?,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var time by remember { mutableStateOf(schedule?.time ?: "08:00") }
    var amount by remember { mutableStateOf(schedule?.amount ?: "1.8") }
    
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = if (schedule == null) "Add Watering Schedule" else "Edit Watering Schedule",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Time input
                Text(
                    text = "Time",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("08:00") },
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Amount input
                Text(
                    text = "Water Amount (L)",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("1.8") },
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6C757D)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Cancel")
                    }
                    
                    Button(
                        onClick = { onSave(time, amount) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF38C070)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(if (schedule == null) "Add" else "Save")
                    }
                }
            }
        }
    }
}

@Composable
fun PlantingSeasonGraph() {
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    
    // Planting season data for Rosemary (example)
    // 0 = Poor, 1 = Good, 2 = Optimal
    val seasonData = listOf(0, 0, 1, 2, 2, 1, 0, 0, 1, 2, 2, 1)
    
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Graph container with dynamic height
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp) // Increased height to accommodate labels
                .background(
                    color = Color(0xFFF8F9FA),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            // Month bars
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                months.forEachIndexed { index, month ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.height(140.dp) // Fixed height for consistent spacing
                    ) {
                        // Season bar - THINNER BARS
                        val barHeight = when (seasonData[index]) {
                            0 -> 25.dp  // Poor - small bar
                            1 -> 60.dp  // Good - medium bar
                            2 -> 95.dp  // Optimal - large bar
                            else -> 25.dp
                        }
                        
                        val barColor = when (seasonData[index]) {
                            0 -> Color(0xFFFF4444)  // Red for poor
                            1 -> Color(0xFFFF8C00)  // Orange for good
                            2 -> Color(0xFF4CAF50)  // Green for optimal
                            else -> Color(0xFFFF4444)
                        }
                        
                                                 Box(
                             modifier = Modifier
                                 .width(10.dp) // Even thinner bars (was 14.dp)
                                 .height(barHeight)
                                 .background(
                                     color = barColor,
                                     shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)
                                 )
                         )
                        
                        Spacer(modifier = Modifier.height(8.dp)) // More spacing for labels
                        
                        // Month label
                        Text(
                            text = month,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
} 