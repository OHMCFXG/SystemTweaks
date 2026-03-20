package moe.ice.systemtweaks.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = AquaSoft,
    onPrimary = DeepSlate,
    secondary = Sand,
    onSecondary = DeepSlate,
    tertiary = Frost,
    background = DeepSlate,
    onBackground = Mist,
    surface = CardDark,
    surfaceVariant = PanelDark,
    onSurface = Mist,
    onSurfaceVariant = AquaSoft,
    outline = LineDark
)

private val LightColorScheme = lightColorScheme(
    primary = Aqua,
    onPrimary = Mist,
    secondary = Slate,
    onSecondary = Mist,
    tertiary = Sand,
    background = Cloud,
    onBackground = Ink,
    surface = CardLight,
    surfaceVariant = Frost,
    onSurface = Ink,
    onSurfaceVariant = Slate,
    outline = LineLight
)

@Composable
fun SystemTweaksTheme(
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
