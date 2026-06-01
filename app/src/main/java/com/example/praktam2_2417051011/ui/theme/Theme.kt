package com.example.praktam2_2417051011.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable

private val AppcolorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueSecondary,
    background = WhiteBackground,
    surface = CardSurface,
    onPrimary = Color.White,
    onSurface = onPrimaryText,
    onSecondary = onSecondaryText,
    onBackground = BlueHeadline,
    surfaceVariant = SearchFieldBackground,
    secondaryContainer = CategoryCardBackground
)

@Composable
fun PrakTam2_2417051011Theme(content: @Composable () -> Unit){
    MaterialTheme(
        colorScheme = AppcolorScheme,
        typography = Typography,
        content = content
    )
}