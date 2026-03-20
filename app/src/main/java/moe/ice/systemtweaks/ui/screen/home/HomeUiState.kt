package moe.ice.systemtweaks.ui.screen.home

import moe.ice.systemtweaks.model.HookTarget
import moe.ice.systemtweaks.model.SettingGroup
import moe.ice.systemtweaks.model.TweakToggle

data class HomeUiState(
    val sections: List<HomeSection>,
    val enabledScopes: List<HookTarget>,
)

data class HomeSection(
    val group: SettingGroup,
    val toggles: List<TweakToggle>,
)
