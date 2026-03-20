package moe.ice.systemtweaks.hook

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import moe.ice.systemtweaks.BuildConfig
import moe.ice.systemtweaks.hook.feature.FeatureRegistry
import moe.ice.systemtweaks.hook.util.XposedLog

@InjectYukiHookWithXposed(entryClassName = "systemtweaks")
class HookEntry : IYukiHookXposedInit {
    override fun onInit() = configs {
        debugLog {
            tag = "SystemTweaks"
        }
        isDebug = BuildConfig.DEBUG
    }

    override fun onHook() = encase {
        FeatureRegistry.features.forEach { feature ->
            XposedLog.info("Registering feature ${feature.definition.key}")
            loadHooker(feature)
        }
    }
}
