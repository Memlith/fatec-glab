package com.fatec.glab_mobile.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = PrimaryForegroundDark,
    primaryContainer = SecondaryDark,
    onPrimaryContainer = SecondaryForegroundDark,
    secondary = SecondaryDark,
    onSecondary = SecondaryForegroundDark,
    secondaryContainer = SecondaryDark,
    onSecondaryContainer = SecondaryForegroundDark,
    tertiary = AccentDark,
    onTertiary = AccentForegroundDark,
    background = BackgroundDark,
    onBackground = ForegroundDark,
    surface = CardDark,
    onSurface = ForegroundDark,
    surfaceVariant = MutedDark,
    onSurfaceVariant = MutedForegroundDark,
    error = DestructiveDark,
    onError = DestructiveForegroundDark,
    outline = BorderDark,
    outlineVariant = InputDark
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = PrimaryForeground,
    primaryContainer = Secondary,
    onPrimaryContainer = SecondaryForeground,
    secondary = Secondary,
    onSecondary = SecondaryForeground,
    secondaryContainer = Secondary,
    onSecondaryContainer = SecondaryForeground,
    tertiary = Accent,
    onTertiary = AccentForeground,
    background = Background,
    onBackground = Foreground,
    surface = Card,
    onSurface = Foreground,
    surfaceVariant = Muted,
    onSurfaceVariant = MutedForeground,
    error = Destructive,
    onError = DestructiveForeground,
    outline = Border,
    outlineVariant = Input
)

@Composable
fun GLabMobileTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
