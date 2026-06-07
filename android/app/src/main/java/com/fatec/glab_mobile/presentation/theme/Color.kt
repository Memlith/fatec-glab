package com.fatec.glab_mobile.presentation.theme

import androidx.compose.ui.graphics.Color

// Light Mode Colors
val Background = Color(0xFFFFFFFF)
val Foreground = Color(0xFF242426)
val Card = Color(0xFFFFFFFF)
val Primary = Color(0xFF353538)
val PrimaryForeground = Color(0xFFFAFAFA)
val Secondary = Color(0xFFF6F6F6)
val SecondaryForeground = Color(0xFF353538)
val Muted = Color(0xFFF6F6F6)
val MutedForeground = Color(0xFF8C8C8C)
val Accent = Color(0xFFF6F6F6)
val AccentForeground = Color(0xFF353538)
val Destructive = Color(0xFFB54444)
val DestructiveForeground = Color(0xFFFFFFFF)
val Border = Color(0xFFEBEBEB)
val Input = Color(0xFFEBEBEB)
val Ring = Color(0xFFB3B3B3)

// Dark Mode Colors
val BackgroundDark = Color(0xFF242426)
val ForegroundDark = Color(0xFFFAFAFA)
val CardDark = Color(0xFF353538)
val PrimaryDark = Color(0xFFEBEBEB)
val PrimaryForegroundDark = Color(0xFF353538)
val SecondaryDark = Color(0xFF3E3E42)
val SecondaryForegroundDark = Color(0xFFFAFAFA)
val MutedDark = Color(0xFF3E3E42)
val MutedForegroundDark = Color(0xFF8C8C8C)
val AccentDark = Color(0xFF3E3E42)
val AccentForegroundDark = Color(0xFFFAFAFA)
val DestructiveDark = Color(0xFFB87070)
val DestructiveForegroundDark = Color(0xFF353538)
val BorderDark = Color(0x1AFFFFFF)
val InputDark = Color(0x26FFFFFF)
val RingDark = Color(0xFF8C8C8C)

// Course Colors (matching frontend)
val CourseDSM = Color(0xFF4f46e5)        // Indigo
val CourseCOMEX = Color(0xFFfacc15)     // Yellow
val CourseREDES = Color(0xFFb91c1c)    // Red
val CourseADS = Color(0xFF14b8a6)       // Teal
val CourseGESTAO_EMP_V = Color(0xFF047857) // Emerald
val CourseGESTAO_EMP_N = Color(0xFF06b6d4) // Cyan
val CourseGESTAO_SERVICOS = Color(0xFF1e40af) // Blue
val CourseLOG_AERO = Color(0xFFf59e0b)  // Amber
val CourseDefault = Color(0xFFf97316)   // Orange

fun getCourseColor(type: String): Color {
    return when (type.uppercase()) {
        "DSM" -> CourseDSM
        "COMEX" -> CourseCOMEX
        "REDES" -> CourseREDES
        "ADS" -> CourseADS
        "GESTAO-EMP-V" -> CourseGESTAO_EMP_V
        "GESTAO-EMP-N" -> CourseGESTAO_EMP_N
        "GESTAO-SERVICOS" -> CourseGESTAO_SERVICOS
        "LOG-AERO" -> CourseLOG_AERO
        else -> CourseDefault
    }
}
