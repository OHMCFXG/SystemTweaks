package moe.ice.systemtweaks.hook.targets.systemui

import moe.ice.systemtweaks.hook.feature.SystemFeature

object SystemUiHookGroup {
    val features: List<SystemFeature> = listOf(
        HideDeveloperOptionsNotificationFeature,
    )
}
