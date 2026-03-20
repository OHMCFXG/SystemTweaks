package moe.ice.systemtweaks.data.store

import android.content.Context
import com.highcapable.yukihookapi.hook.factory.prefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.ice.systemtweaks.hook.feature.FeatureRegistry
import moe.ice.systemtweaks.model.TweakToggle

class ModuleSettingsRepository(
    context: Context,
) {
    private val preferences = context.applicationContext.prefs(FILE_NAME)

    private val _toggles = MutableStateFlow(loadToggles())
    val toggles: StateFlow<List<TweakToggle>> = _toggles.asStateFlow()

    fun setEnabled(key: String, enabled: Boolean) {
        preferences.edit {
            putBoolean(key, enabled)
        }
        _toggles.value = loadToggles()
    }

    private fun loadToggles(): List<TweakToggle> {
        return FeatureRegistry.definitions.map { definition ->
            TweakToggle(
                definition = definition,
                enabled = preferences.getBoolean(definition.key, false),
            )
        }
    }

    companion object {
        const val FILE_NAME = "module_settings"
    }
}
