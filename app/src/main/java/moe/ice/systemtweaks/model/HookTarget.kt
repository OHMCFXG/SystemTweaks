package moe.ice.systemtweaks.model

import androidx.annotation.StringRes
import moe.ice.systemtweaks.R

enum class HookTarget(
    val packageName: String,
    @param:StringRes
    val labelRes: Int,
) {
    Android(
        packageName = "android",
        labelRes = R.string.target_android,
    ),
    SystemUi(
        packageName = "com.android.systemui",
        labelRes = R.string.target_systemui,
    ),
}
