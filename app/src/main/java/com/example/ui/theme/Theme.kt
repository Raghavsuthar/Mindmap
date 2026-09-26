package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val DarkNeuroColorScheme = darkColorScheme(
    primary = CircuitPrimary,
    onPrimary = Color.Black,
    primaryContainer = CircuitContainerDark,
    onPrimaryContainer = CircuitOnContainerDark,
    secondary = TransmitterPrimary,
    onSecondary = Color.Black,
    secondaryContainer = TransmitterContainerDark,
    onSecondaryContainer = TransmitterOnContainerDark,
    tertiary = DrugPrimary,
    onTertiary = Color.White,
    tertiaryContainer = DrugContainerDark,
    onTertiaryContainer = DrugOnContainerDark,
    background = DarkCanvas,
    onBackground = DarkTextHigh,
    surface = DarkSurface,
    onSurface = DarkTextHigh,
    surfaceVariant = DarkElevatedCard,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkBorder,
    outlineVariant = DarkBorder.copy(alpha = 0.5f)
)

val LightNeuroColorScheme = lightColorScheme(
    primary = CircuitBorder,
    onPrimary = Color.White,
    primaryContainer = CircuitContainerLight,
    onPrimaryContainer = CircuitBorder,
    secondary = TransmitterBorder,
    onSecondary = Color.White,
    secondaryContainer = TransmitterContainerLight,
    onSecondaryContainer = TransmitterBorder,
    tertiary = DrugBorder,
    onTertiary = Color.White,
    tertiaryContainer = DrugContainerLight,
    onTertiaryContainer = DrugBorder,
    background = LightCanvas,
    onBackground = LightTextHigh,
    surface = LightSurface,
    onSurface = LightTextHigh,
    surfaceVariant = LightElevatedCard,
    onSurfaceVariant = LightTextSecondary,
    outline = LightBorder,
    outlineVariant = LightBorder.copy(alpha = 0.6f)
)

data class LayerColors(
    val circuit: Color,
    val circuitBorder: Color,
    val circuitContainer: Color,
    val circuitOnContainer: Color,
    val transmitter: Color,
    val transmitterBorder: Color,
    val transmitterContainer: Color,
    val transmitterOnContainer: Color,
    val syndrome: Color,
    val syndromeBorder: Color,
    val syndromeContainer: Color,
    val syndromeOnContainer: Color,
    val drug: Color,
    val drugBorder: Color,
    val drugContainer: Color,
    val drugOnContainer: Color,
    val elevatedCard: Color,
    val hairlineBorder: Color,
    val textHigh: Color,
    val textSecondary: Color
)

val LocalLayerColors = staticCompositionLocalOf {
    LayerColors(
        circuit = CircuitPrimary,
        circuitBorder = CircuitBorder,
        circuitContainer = CircuitContainerDark,
        circuitOnContainer = CircuitOnContainerDark,
        transmitter = TransmitterPrimary,
        transmitterBorder = TransmitterBorder,
        transmitterContainer = TransmitterContainerDark,
        transmitterOnContainer = TransmitterOnContainerDark,
        syndrome = SyndromePrimary,
        syndromeBorder = SyndromeBorder,
        syndromeContainer = SyndromeContainerDark,
        syndromeOnContainer = SyndromeOnContainerDark,
        drug = DrugPrimary,
        drugBorder = DrugBorder,
        drugContainer = DrugContainerDark,
        drugOnContainer = DrugOnContainerDark,
        elevatedCard = DarkElevatedCard,
        hairlineBorder = DarkBorder,
        textHigh = DarkTextHigh,
        textSecondary = DarkTextSecondary
    )
}

@Composable
fun NeuroMapTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkNeuroColorScheme else LightNeuroColorScheme
    val layerColors = if (darkTheme) {
        LayerColors(
            circuit = CircuitPrimary,
            circuitBorder = CircuitBorder,
            circuitContainer = CircuitContainerDark,
            circuitOnContainer = CircuitOnContainerDark,
            transmitter = TransmitterPrimary,
            transmitterBorder = TransmitterBorder,
            transmitterContainer = TransmitterContainerDark,
            transmitterOnContainer = TransmitterOnContainerDark,
            syndrome = SyndromePrimary,
            syndromeBorder = SyndromeBorder,
            syndromeContainer = SyndromeContainerDark,
            syndromeOnContainer = SyndromeOnContainerDark,
            drug = DrugPrimary,
            drugBorder = DrugBorder,
            drugContainer = DrugContainerDark,
            drugOnContainer = DrugOnContainerDark,
            elevatedCard = DarkElevatedCard,
            hairlineBorder = DarkBorder,
            textHigh = DarkTextHigh,
            textSecondary = DarkTextSecondary
        )
    } else {
        LayerColors(
            circuit = CircuitBorder,
            circuitBorder = CircuitBorder,
            circuitContainer = CircuitContainerLight,
            circuitOnContainer = CircuitBorder,
            transmitter = TransmitterBorder,
            transmitterBorder = TransmitterBorder,
            transmitterContainer = TransmitterContainerLight,
            transmitterOnContainer = TransmitterBorder,
            syndrome = SyndromeBorder,
            syndromeBorder = SyndromeBorder,
            syndromeContainer = SyndromeContainerLight,
            syndromeOnContainer = SyndromeBorder,
            drug = DrugBorder,
            drugBorder = DrugBorder,
            drugContainer = DrugContainerLight,
            drugOnContainer = DrugBorder,
            elevatedCard = LightElevatedCard,
            hairlineBorder = LightBorder,
            textHigh = LightTextHigh,
            textSecondary = LightTextSecondary
        )
    }

    CompositionLocalProvider(LocalLayerColors provides layerColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
