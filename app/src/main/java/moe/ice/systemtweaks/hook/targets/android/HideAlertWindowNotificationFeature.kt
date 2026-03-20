package moe.ice.systemtweaks.hook.targets.android

import com.highcapable.kavaref.KavaRef.Companion.resolve
import moe.ice.systemtweaks.hook.feature.SystemFeature
import moe.ice.systemtweaks.hook.util.XposedLog
import moe.ice.systemtweaks.model.HookTarget
import moe.ice.systemtweaks.model.SettingGroup
import moe.ice.systemtweaks.model.TweakDefinition
import moe.ice.systemtweaks.R

object HideAlertWindowNotificationFeature : SystemFeature() {
    override val definition = TweakDefinition(
        key = "hide_alert_window_notification",
        titleRes = R.string.tweak_hide_alert_window_notification_title,
        summaryRes = R.string.tweak_hide_alert_window_notification_summary,
        group = SettingGroup.Notifications,
        targets = setOf(HookTarget.Android),
    )

    override fun onHook() =
        loadSystemFeature {
            XposedLog.info("Resolving alert window notification class $ALERT_WINDOW_NOTIFICATION_CLASS")
            val sourceClass = ALERT_WINDOW_NOTIFICATION_CLASS.toClass().resolve()
            XposedLog.info("Resolved alert window notification class")

            sourceClass.firstMethod {
                modifiers()
                name = POST_METHOD
                emptyParameters()
                returnType = Void.TYPE
            }.hook {
                before {
                    XposedLog.info("AlertWindowNotification.post hit, suppressing notification")
                    resultNull()
                }
            }

            XposedLog.info("Installed Yuki hook for AlertWindowNotification.post")
        }

    private const val ALERT_WINDOW_NOTIFICATION_CLASS =
        "com.android.server.wm.AlertWindowNotification"
    private const val POST_METHOD = "post"
}
