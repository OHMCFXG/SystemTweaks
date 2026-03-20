package moe.ice.systemtweaks.hook.targets.android

import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.kavaref.condition.type.Modifiers
import moe.ice.systemtweaks.hook.feature.SystemFeature
import moe.ice.systemtweaks.hook.util.XposedLog
import moe.ice.systemtweaks.model.HookTarget
import moe.ice.systemtweaks.model.SettingGroup
import moe.ice.systemtweaks.model.TweakDefinition
import moe.ice.systemtweaks.R

object HideVpnNotificationFeature : SystemFeature() {
    override val definition = TweakDefinition(
        key = "hide_vpn_notification",
        titleRes = R.string.tweak_hide_vpn_notification_title,
        summaryRes = R.string.tweak_hide_vpn_notification_summary,
        group = SettingGroup.Notifications,
        targets = setOf(HookTarget.Android),
    )

    override fun onHook() =
        loadSystemFeature {
            XposedLog.info("Resolving VPN source class $VPN_EXT_IMPL_CLASS")
            val sourceClass = VPN_EXT_IMPL_CLASS.toClass().resolve()
            XposedLog.info("Resolved VPN source class")

            sourceClass.firstMethod {
                modifiers(Modifiers.PUBLIC)
                name = SHOW_NOTIFICATION_METHOD
                parameters(
                    String::class,
                    Int::class,
                    Int::class,
                    String::class,
                    PENDING_INTENT_CLASS,
                    VPN_CONFIG_CLASS,
                )
                returnType = Void.TYPE
            }.hook {
                before {
                    val currentPackage = args.getOrNull(CURRENT_PACKAGE_INDEX) as? String
                    XposedLog.info(
                        "VpnExtImpl.showNotification hit" +
                            if (currentPackage.isNullOrBlank()) "" else ", package=$currentPackage",
                    )
                    resultNull()
                }
            }

            XposedLog.info("Installed Yuki hook for VpnExtImpl.showNotification")
        }

    private const val VPN_EXT_IMPL_CLASS = "com.android.server.connectivity.VpnExtImpl"
    private const val SHOW_NOTIFICATION_METHOD = "showNotification"
    private const val PENDING_INTENT_CLASS = "android.app.PendingIntent"
    private const val VPN_CONFIG_CLASS = "com.android.internal.net.VpnConfig"
    private const val CURRENT_PACKAGE_INDEX = 3
}
