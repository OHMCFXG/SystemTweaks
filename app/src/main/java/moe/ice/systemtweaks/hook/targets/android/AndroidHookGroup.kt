package moe.ice.systemtweaks.hook.targets.android

import moe.ice.systemtweaks.hook.feature.SystemFeature

object AndroidHookGroup {
    val features: List<SystemFeature> = listOf(
        HideVpnNotificationFeature,
        HideAlertWindowNotificationFeature,
    )
}
