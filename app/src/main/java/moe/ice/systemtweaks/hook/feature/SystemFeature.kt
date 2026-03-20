package moe.ice.systemtweaks.hook.feature

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.param.PackageParam
import moe.ice.systemtweaks.data.store.ModuleSettingsRepository
import moe.ice.systemtweaks.hook.util.XposedLog
import moe.ice.systemtweaks.model.HookTarget
import moe.ice.systemtweaks.model.TweakDefinition

abstract class SystemFeature : YukiBaseHooker() {
    abstract val definition: TweakDefinition

    protected inline fun loadSystemFeature(crossinline initiate: PackageParam.() -> Unit) {
        loadSystem {
            runIfEnabled(initiate)
        }
    }

    protected inline fun loadAppFeature(
        target: HookTarget,
        crossinline initiate: PackageParam.() -> Unit,
    ) {
        loadApp(name = target.packageName) {
            runIfEnabled(initiate)
        }
    }

    protected inline fun PackageParam.runIfEnabled(
        crossinline initiate: PackageParam.() -> Unit,
    ) {
        val enabled = prefs(ModuleSettingsRepository.FILE_NAME).getBoolean(definition.key, false)
        XposedLog.info("Feature ${definition.key} enabled=$enabled in $packageName")
        if (!enabled) {
            return
        }

        runCatching {
            initiate()
        }.onFailure { throwable ->
            XposedLog.error("Failed to install feature ${definition.key}", throwable)
        }
    }
}
