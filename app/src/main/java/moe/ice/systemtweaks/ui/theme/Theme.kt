package moe.ice.systemtweaks.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

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
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && darkTheme -> dynamicDarkColorScheme(context)
        dynamicColor -> dynamicLightColorScheme(context)
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
