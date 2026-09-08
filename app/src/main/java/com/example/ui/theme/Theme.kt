package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = ForestGreenPrimary,
    onPrimary = ForestGreenOnPrimary,
    primaryContainer = ForestGreenContainer,
    onPrimaryContainer = ForestGreenOnContainer,
    secondary = EarthBrownSecondary,
    onSecondary = EarthBrownOnSecondary,
    secondaryContainer = EarthBrownContainer,
    onSecondaryContainer = EarthBrownOnContainer,
    tertiary = GoldenWheatTertiary,
    onTertiary = GoldenWheatOnTertiary,
    tertiaryContainer = GoldenWheatContainer,
    onTertiaryContainer = GoldenWheatOnContainer,
    background = RuralWarmBackground,
    onBackground = RuralOnSurface,
    surface = RuralWarmSurface,
    onSurface = RuralOnSurface,
    surfaceVariant = RuralWarmSurfaceVariant,
    onSurfaceVariant = RuralOnSurfaceVariant,
    outline = RuralOutline,
    outlineVariant = RuralOutlineVariant
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkGreenPrimary,
    onPrimary = ForestGreenOnContainer,
    primaryContainer = DarkGreenContainer,
    onPrimaryContainer = ForestGreenContainer,
    secondary = DarkBrownSecondary,
    onSecondary = EarthBrownOnContainer,
    secondaryContainer = DarkBrownContainer,
    onSecondaryContainer = EarthBrownContainer,
    tertiary = GoldenWheatContainer,
    onTertiary = GoldenWheatOnContainer,
    background = DarkBackground,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurface,
    outline = RuralOutline
)

@Composable
fun KisanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
