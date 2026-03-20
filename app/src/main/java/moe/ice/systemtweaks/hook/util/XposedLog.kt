package moe.ice.systemtweaks.hook.util

import android.util.Log
import de.robv.android.xposed.XposedBridge

object XposedLog {
    private const val TAG = "SystemTweaks"

    fun info(message: String) {
        Log.i(TAG, message)
        XposedBridge.log("$TAG: $message")
    }

    fun error(
        message: String,
        throwable: Throwable,
    ) {
        Log.e(TAG, message, throwable)
        XposedBridge.log("$TAG: $message\n${Log.getStackTraceString(throwable)}")
    }
}
