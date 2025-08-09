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
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
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

@Composable
fun NumberInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    minValue: Int = 0,
    maxValue: Int = 999,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.isEmpty() || newValue.toIntOrNull() != null) {
                    val intValue = newValue.toIntOrNull() ?: 0
                    if (intValue >= minValue && intValue <= maxValue) {
                        onValueChange(newValue)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Color.LightGray) },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF4CAF50),
                unfocusedBorderColor = Color(0xFF4CAF50),
                focusedContainerColor = Color(0xFFFFF8F9),
                unfocusedContainerColor = Color(0xFFFFF8F9)
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCustomPlantScreen(
    onBackClick: () -> Unit,
    onSavePlant: (PlantItem) -> Unit
) {
    var plantName by remember { mutableStateOf("") }
    var plantCategory by remember { mutableStateOf("") }
    var plantStatus by remember { mutableStateOf("Growing") }
    var waterAmount by remember { mutableStateOf("150ml") }
    
    // Watering and Maintenance controls
    var wateringVolume by remember { mutableStateOf(1.8f) }
    var emergencyVolume by remember { mutableStateOf(0.5f) }
    
    // Temperature Range controls
    var tempMin by remember { mutableStateOf(20f) }
    var tempMax by remember { mutableStateOf(25f) }
    
    // Environmental preferences
    var humidityMin by remember { mutableStateOf("50") }
    var humidityMax by remember { mutableStateOf("70") }
    var lightRequirement by remember { mutableStateOf("Full Sun to Partial Shade") }
    var lightHours by remember { mutableStateOf("6-8") }
    
    // Watering preferences
    var wateringFrequency by remember { mutableStateOf("Twice daily") }
    var schedules by remember { mutableStateOf(listOf(
        WateringSchedule("08:00", "1.8"),
        WateringSchedule("18:00", "1.2")
    ).toMutableList()) }
    
    // Growth preferences
    var growthDuration by remember { mutableStateOf("70") }
    var harvestTime by remember { mutableStateOf("60-90") }
    var expectedYield by remember { mutableStateOf("200-300g") }
    
    var showAddScheduleDialog by remember { mutableStateOf(false) }
    var showEditScheduleDialog by remember { mutableStateOf<WateringSchedule?>(null) }
    
    val context = LocalContext.current
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Custom Plant",
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
                                contentDescription = "Back",
                                modifier = Modifier.size(24.dp)
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
                        onClick = {
                            if (plantName.isNotBlank() && plantCategory.isNotBlank()) {
                                val newPlant = PlantItem(
                                    name = plantName,
                                    species = "", // Removed species
                                    status = plantStatus,
                                    waterAmount = waterAmount
                                )
                                onSavePlant(newPlant)
                                Toast.makeText(context, "Plant added successfully!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Please fill in plant name and category", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save Plant"
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
            // Basic Plant Information
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
                            text = "Basic Information",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Plant Image using rosemary.png
                        Image(
                            painter = painterResource(id = R.drawable.rosemary),
                            contentDescription = "Plant Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 120.dp, max = 200.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Fit
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Plant Name
                        Text(
                            text = "Plant Name *",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = plantName,
                            onValueChange = { plantName = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Enter plant name", color = Color.LightGray) },
                            singleLine = true,
                            shape = RoundedCornerShape(20.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF4CAF50),
                                unfocusedBorderColor = Color(0xFF4CAF50),
                                focusedContainerColor = Color(0xFFFFF8F9),
                                unfocusedContainerColor = Color(0xFFFFF8F9)
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Plant Category
                        Text(
                            text = "Category *",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        var expanded by remember { mutableStateOf(false) }
                        val categories = listOf("Fruits", "Vegetables", "Herbs")
                        
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = plantCategory,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                placeholder = { Text("Select a category", color = Color.LightGray) },
                                shape = RoundedCornerShape(20.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF4CAF50),
                                    unfocusedBorderColor = Color(0xFF4CAF50),
                                    focusedContainerColor = Color(0xFFFFF8F9),
                                    unfocusedContainerColor = Color(0xFFFFF8F9)
                                )
                            )
                            
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(Color(0xFFFFF8F9))
                            ) {
                                categories.forEach { category ->
                                    DropdownMenuItem(
                                        text = { Text(category) },
                                        onClick = {
                                            plantCategory = category
                                            expanded = false
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            // Watering and Maintenance Card
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
                        
                        // Watering Volume Control
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = "Watering Volume",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                    )
                                    Text(
                                        text = "Regular watering amount",
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
                                        tint = Color(0xFF38C070),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = "${wateringVolume}L",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                        color = Color(0xFF38C070)
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            // Volume Slider
                            Slider(
                                value = wateringVolume,
                                onValueChange = { wateringVolume = it },
                                valueRange = 0.5f..5.0f,
                                steps = 45,
                                modifier = Modifier.height(24.dp),
                                colors = SliderDefaults.colors(
                                    thumbColor = Color(0xFF38C070),
                                    activeTrackColor = Color(0xFF38C070),
                                    inactiveTrackColor = Color(0xFFE0E0E0)
                                )
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Emergency Volume Control
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = "Emergency Volume",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                    )
                                    Text(
                                        text = "Additional watering for stress conditions",
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
                                        contentDescription = "Emergency Water",
                                        tint = Color(0xFFFF6B35),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = "+${emergencyVolume}L",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                        color = Color(0xFFFF6B35)
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            // Emergency Volume Slider
                            Slider(
                                value = emergencyVolume,
                                onValueChange = { emergencyVolume = it },
                                valueRange = 0.1f..2.0f,
                                steps = 19,
                                modifier = Modifier.height(24.dp),
                                colors = SliderDefaults.colors(
                                    thumbColor = Color(0xFFFF6B35),
                                    activeTrackColor = Color(0xFFFF6B35),
                                    inactiveTrackColor = Color(0xFFE0E0E0)
                                )
                            )
                        }
                    }
                }
            }
            
            // Temperature Range Card
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
                            text = "Temperature Range Preference",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Temperature Range Display
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Thermostat,
                                contentDescription = "Temperature",
                                tint = Color(0xFF4CAF50),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${tempMin.toInt()}°C - ${tempMax.toInt()}°C",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                color = Color(0xFF4CAF50)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Min Temperature Control
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Minimum Temperature",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                )
                                Text(
                                    text = "${tempMin.toInt()}°C",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    color = Color(0xFF4CAF50)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Slider(
                                value = tempMin,
                                onValueChange = { 
                                    tempMin = it
                                    if (tempMin > tempMax) tempMax = tempMin + 5f
                                },
                                valueRange = 10f..35f,
                                steps = 25,
                                modifier = Modifier.height(24.dp),
                                colors = SliderDefaults.colors(
                                    thumbColor = Color(0xFF4CAF50),
                                    activeTrackColor = Color(0xFF4CAF50),
                                    inactiveTrackColor = Color(0xFFE0E0E0)
                                )
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Max Temperature Control
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Maximum Temperature",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                )
                                Text(
                                    text = "${tempMax.toInt()}°C",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    color = Color(0xFF4CAF50)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Slider(
                                value = tempMax,
                                onValueChange = { 
                                    tempMax = it
                                    if (tempMax < tempMin) tempMin = tempMax - 5f
                                },
                                valueRange = (tempMin + 1f)..40f,
                                steps = (40 - (tempMin + 1f)).toInt(),
                                modifier = Modifier.height(24.dp),
                                colors = SliderDefaults.colors(
                                    thumbColor = Color(0xFF4CAF50),
                                    activeTrackColor = Color(0xFF4CAF50),
                                    inactiveTrackColor = Color(0xFFE0E0E0)
                                )
                            )
                        }
                    }
                }
            }
            
            // Environmental Preferences
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
                        
                        // Humidity Range
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Min Humidity Control
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(
                                            text = "Minimum Humidity",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                        )
                                        Text(
                                            text = "Lowest acceptable humidity level",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Thermostat,
                                            contentDescription = "Humidity",
                                            tint = Color(0xFF4CAF50),
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            text = "${humidityMin}%",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                            color = Color(0xFF4CAF50)
                                        )
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Slider(
                                    value = humidityMin.toFloat(),
                                    onValueChange = { 
                                        humidityMin = it.toInt().toString()
                                        if (humidityMin.toInt() > humidityMax.toInt()) {
                                            humidityMax = (humidityMin.toInt() + 10).toString()
                                        }
                                    },
                                    valueRange = 0f..100f,
                                    steps = 100,
                                    modifier = Modifier.height(24.dp),
                                    colors = SliderDefaults.colors(
                                        thumbColor = Color(0xFF4CAF50),
                                        activeTrackColor = Color(0xFF4CAF50),
                                        inactiveTrackColor = Color(0xFFE0E0E0)
                                    )
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Max Humidity Control
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(
                                            text = "Maximum Humidity",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                        )
                                        Text(
                                            text = "Highest acceptable humidity level",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Thermostat,
                                            contentDescription = "Humidity",
                                            tint = Color(0xFF4CAF50),
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            text = "${humidityMax}%",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                            color = Color(0xFF4CAF50)
                                        )
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Slider(
                                    value = humidityMax.toFloat(),
                                    onValueChange = { 
                                        humidityMax = it.toInt().toString()
                                        if (humidityMax.toInt() < humidityMin.toInt()) {
                                            humidityMin = (humidityMax.toInt() - 10).toString()
                                        }
                                    },
                                    valueRange = (humidityMin.toInt() + 1).toFloat()..100f,
                                    steps = (100 - (humidityMin.toInt() + 1)),
                                    modifier = Modifier.height(24.dp),
                                    colors = SliderDefaults.colors(
                                        thumbColor = Color(0xFF4CAF50),
                                        activeTrackColor = Color(0xFF4CAF50),
                                        inactiveTrackColor = Color(0xFFE0E0E0)
                                    )
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Hours of Sunlight",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = lightHours,
                            onValueChange = { lightHours = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("6-8", color = Color.LightGray) },
                            singleLine = true,
                            shape = RoundedCornerShape(20.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF4CAF50),
                                unfocusedBorderColor = Color(0xFF4CAF50),
                                focusedContainerColor = Color(0xFFFFF8F9),
                                unfocusedContainerColor = Color(0xFFFFF8F9)
                            )
                        )
                    }
                }
            }
            
            // Watering Preferences
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Watering Preferences",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                            
                            IconButton(
                                onClick = { showAddScheduleDialog = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add Schedule"
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Watering Schedules
                        Text(
                            text = "Watering Schedules",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        schedules.forEachIndexed { index: Int, schedule: WateringSchedule ->
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
                                    Column {
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
                                    
                                    IconButton(
                                        onClick = { showEditScheduleDialog = schedule }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    
                                    IconButton(
                                        onClick = { 
                                            schedules = schedules.toMutableList().apply { removeAt(index) }
                                        }
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
                    }
                }
                
                // Add Schedule Dialog
                if (showAddScheduleDialog) {
                    CustomScheduleDialog(
                        schedule = null,
                        onDismiss = { showAddScheduleDialog = false },
                        onSave = { time, amount ->
                            schedules = schedules.toMutableList().apply {
                                add(WateringSchedule(time, amount))
                            }
                            showAddScheduleDialog = false
                        }
                    )
                }
                
                // Edit Schedule Dialog
                showEditScheduleDialog?.let { schedule ->
                    CustomScheduleDialog(
                        schedule = schedule,
                        onDismiss = { showEditScheduleDialog = null },
                        onSave = { time, amount ->
                            val index = schedules.indexOf(schedule)
                            if (index != -1) {
                                schedules = schedules.toMutableList().apply {
                                    set(index, WateringSchedule(time, amount))
                                }
                            }
                            showEditScheduleDialog = null
                        }
                    )
                }
            }
            
            // Growth Preferences
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
                            text = "Growth Preferences",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Growth Duration
                        NumberInputField(
                            value = growthDuration,
                            onValueChange = { growthDuration = it },
                            label = "Growth Duration (days)",
                            placeholder = "70",
                            minValue = 1,
                            maxValue = 365,
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Harvest Time
                        Text(
                            text = "Harvest Time (days)",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = harvestTime,
                            onValueChange = { harvestTime = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("60-90", color = Color.LightGray) },
                            singleLine = true,
                            shape = RoundedCornerShape(20.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF4CAF50),
                                unfocusedBorderColor = Color(0xFF4CAF50),
                                focusedContainerColor = Color(0xFFFFF8F9),
                                unfocusedContainerColor = Color(0xFFFFF8F9)
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomScheduleDialog(
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
                    placeholder = { Text("08:00", color = Color.LightGray) },
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF4CAF50),
                        unfocusedBorderColor = Color(0xFF4CAF50),
                        focusedContainerColor = Color(0xFFFFF8F9),
                        unfocusedContainerColor = Color(0xFFFFF8F9)
                    )
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Amount input
                NumberInputField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = "Water Amount (L)",
                    placeholder = "1.8",
                    minValue = 0,
                    maxValue = 10,
                    modifier = Modifier.fillMaxWidth()
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