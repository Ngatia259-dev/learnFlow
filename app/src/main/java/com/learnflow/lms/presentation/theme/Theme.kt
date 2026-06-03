package com.learnflow.lms.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryAmber,
    onPrimary = TextOnPrimary,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = DarkBrown,
    secondary = DarkBrown,
    onSecondary = TextOnPrimary,
    secondaryContainer = OffWhite,
    onSecondaryContainer = DarkBrown,
    tertiary = Success,
    onTertiary = TextOnPrimary,
    background = Background,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = OffWhite,
    onSurfaceVariant = TextSecondary,
    error = Error,
    onError = TextOnPrimary
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryAmber,
    onPrimary = DarkBrown,
    primaryContainer = PrimaryDark,
    onPrimaryContainer = OffWhite,
    secondary = OffWhite,
    onSecondary = DarkBrown,
    secondaryContainer = DarkSurface,
    onSecondaryContainer = OffWhite,
    tertiary = Success,
    onTertiary = DarkBrown,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurface,
    onSurfaceVariant = TextSecondary,
    error = Error,
    onError = TextOnPrimary
)

@Composable
fun LearnFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
