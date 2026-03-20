package moe.ice.systemtweaks.model

import androidx.annotation.StringRes

data class TweakDefinition(
    val key: String,
    @param:StringRes
    val titleRes: Int,
    @param:StringRes
    val summaryRes: Int,
    val group: SettingGroup,
    val targets: Set<HookTarget>,
    val requiresRestart: Boolean = false,
)
