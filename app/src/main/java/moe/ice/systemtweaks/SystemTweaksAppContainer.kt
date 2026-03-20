package moe.ice.systemtweaks

import android.content.Context
import moe.ice.systemtweaks.data.store.ModuleSettingsRepository

class SystemTweaksAppContainer(context: Context) {
    private val appContext = context.applicationContext

    val moduleSettingsRepository by lazy(LazyThreadSafetyMode.NONE) {
        ModuleSettingsRepository(appContext)
    }
}
