package moe.ice.systemtweaks.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import moe.ice.systemtweaks.data.store.ModuleSettingsRepository
import moe.ice.systemtweaks.model.HookTarget
import moe.ice.systemtweaks.model.SettingGroup
import moe.ice.systemtweaks.model.TweakToggle

@Composable
fun HomeRoute(repository: ModuleSettingsRepository) {
    val toggles by repository.toggles.collectAsState()
    val uiState = remember(toggles) {
        toggles.toHomeUiState()
    }

    HomeScreen(
        uiState = uiState,
        onToggleChanged = repository::setEnabled,
    )
}

private fun List<TweakToggle>.toHomeUiState(): HomeUiState {
    val togglesByGroup = groupBy { it.definition.group }
    val sections = SettingGroup.entries.mapNotNull { group ->
        togglesByGroup[group]?.takeIf { it.isNotEmpty() }?.let { toggles ->
            HomeSection(
                group = group,
                toggles = toggles,
            )
        }
    }
    val enabledScopes = asSequence()
        .filter { it.enabled }
        .flatMap { it.definition.targets.asSequence() }
        .distinct()
        .sortedBy(HookTarget::packageName)
        .toList()

    return HomeUiState(
        sections = sections,
        enabledScopes = enabledScopes,
    )
}
