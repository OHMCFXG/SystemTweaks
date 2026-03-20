package moe.ice.systemtweaks.hook.feature

import moe.ice.systemtweaks.hook.targets.android.AndroidHookGroup
import moe.ice.systemtweaks.hook.targets.systemui.SystemUiHookGroup

object FeatureRegistry {
    val features: List<SystemFeature> =
        AndroidHookGroup.features + SystemUiHookGroup.features

    val definitions = features.map(SystemFeature::definition)
}
