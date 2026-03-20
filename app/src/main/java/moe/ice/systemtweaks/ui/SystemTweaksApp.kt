package moe.ice.systemtweaks.ui

import androidx.compose.runtime.Composable
import moe.ice.systemtweaks.SystemTweaksAppContainer
import moe.ice.systemtweaks.ui.screen.home.HomeRoute

@Composable
fun SystemTweaksApp(container: SystemTweaksAppContainer) {
    HomeRoute(repository = container.moduleSettingsRepository)
}
