package com.example.plantmonitoring_v2.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.plantmonitoring_v2.R

// Define the Teachers FontFamily
val TeachersFontFamily = FontFamily(
    Font(R.font.teachers_regular, FontWeight.Normal),
    Font(R.font.teachers_bold, FontWeight.Bold)
)

// Apply custom text styles using the Teachers font
val Typography = Typography(
    // Small Header: Teachers, Bold, 12sp
    labelSmall = TextStyle(
        fontFamily = TeachersFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    ),

    // Header: Teachers, Bold, 38sp
    headlineLarge = TextStyle(
        fontFamily = TeachersFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 38.sp
    ),

    // Title Description: Teachers, Regular, 12sp
    bodySmall = TextStyle(
        fontFamily = TeachersFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),

    // Item Title: Teachers, Bold, 15sp
    titleMedium = TextStyle(
        fontFamily = TeachersFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp
    ),

    // Bold Title Description: Teachers, Bold, 10sp
    labelMedium = TextStyle(
        fontFamily = TeachersFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp
    ),

    // Item Description: Teachers, Regular, 10sp
    bodyMedium = TextStyle(
        fontFamily = TeachersFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    ),

    // Item Value: Teachers, Bold, 32sp
    displayMedium = TextStyle(
        fontFamily = TeachersFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp
    ),

    // Default Body Large fallback
    bodyLarge = TextStyle(
        fontFamily = TeachersFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

// Custom style for Teachers, Regular, 38sp
val headlineRegular = TextStyle(
    fontFamily = TeachersFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 38.sp
)
