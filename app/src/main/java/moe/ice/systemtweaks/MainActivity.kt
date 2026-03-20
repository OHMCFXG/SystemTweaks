package moe.ice.systemtweaks

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import moe.ice.systemtweaks.ui.SystemTweaksApp
import moe.ice.systemtweaks.ui.theme.SystemTweaksTheme

class MainActivity : ComponentActivity() {
    private val appContainer by lazy(LazyThreadSafetyMode.NONE) {
        SystemTweaksAppContainer(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SystemTweaksTheme {
                SystemTweaksApp(container = appContainer)
            }
        }
    }
}
