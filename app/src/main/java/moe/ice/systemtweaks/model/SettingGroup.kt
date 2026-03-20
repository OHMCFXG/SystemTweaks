package moe.ice.systemtweaks.model

import androidx.annotation.StringRes
import moe.ice.systemtweaks.R

enum class SettingGroup(
    @param:StringRes
    val titleRes: Int,
    @param:StringRes
    val descriptionRes: Int,
) {
    Notifications(
        titleRes = R.string.group_notifications_title,
        descriptionRes = R.string.group_notifications_description,
    ),
    System(
        titleRes = R.string.group_system_title,
        descriptionRes = R.string.group_system_description,
    ),
}
