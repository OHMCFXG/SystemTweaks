package moe.ice.systemtweaks.hook.targets.systemui

import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.kavaref.condition.type.Modifiers
import moe.ice.systemtweaks.hook.feature.SystemFeature
import moe.ice.systemtweaks.hook.util.XposedLog
import moe.ice.systemtweaks.model.HookTarget
import moe.ice.systemtweaks.model.SettingGroup
import moe.ice.systemtweaks.model.TweakDefinition
import moe.ice.systemtweaks.R

object HideDeveloperOptionsNotificationFeature : SystemFeature() {
    override val definition = TweakDefinition(
        key = "hide_developer_options_notification",
        titleRes = R.string.tweak_hide_developer_options_notification_title,
        summaryRes = R.string.tweak_hide_developer_options_notification_summary,
        group = SettingGroup.Notifications,
        targets = setOf(HookTarget.SystemUi),
    )

    override fun onHook() =
        loadAppFeature(HookTarget.SystemUi) {
            XposedLog.info("Resolving SystemUI prompt controller $SYSTEM_PROMPT_CONTROLLER_CLASS")
            val controllerClass = SYSTEM_PROMPT_CONTROLLER_CLASS.toClass().resolve()
            XposedLog.info("Resolved SystemUI prompt controller")

            controllerClass.firstMethod {
                modifiers(Modifiers.PUBLIC, Modifiers.FINAL)
                name = UPDATE_DEVELOPER_MODE_METHOD
                emptyParameters()
                returnType = Void.TYPE
            }.hook {
                before {
                    XposedLog.info("SystemPromptController.updateDeveloperMode hit, suppressing notification")
                    resultNull()
                }
            }

            XposedLog.info("Installed Yuki hook for SystemPromptController.updateDeveloperMode")
        }

    private const val SYSTEM_PROMPT_CONTROLLER_CLASS =
        "com.oplus.systemui.statusbar.controller.SystemPromptController"
    private const val UPDATE_DEVELOPER_MODE_METHOD = "updateDeveloperMode"
}
