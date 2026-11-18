package io.github.jqssun.airplay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import io.github.jqssun.airplay.service.AirPlayService

/**
 * Invisible screen that starts or stops the AirPlay receiver for another app, such as a launcher's
 * Control Panel tile, without showing the app. Starting it as an activity puts this app in the
 * foreground for a moment, which Android requires before a foreground service may start.
 *
 * Callers need the DUMP permission (granted over adb to the launcher), the same guard as
 * [SettingsReceiver], so ordinary apps cannot switch the receiver on or off.
 *
 *   am start -n io.github.jqssun.airplay/.ServerControlActivity -a io.github.jqssun.airplay.START_SERVER
 *   am start -n io.github.jqssun.airplay/.ServerControlActivity -a io.github.jqssun.airplay.STOP_SERVER
 */
class ServerControlActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (intent?.action) {
            AirPlayService.ACTION_START_SERVER -> ContextCompat.startForegroundService(
                this, Intent(this, AirPlayService::class.java).setAction(AirPlayService.ACTION_START_SERVER),
            )
            AirPlayService.ACTION_STOP_SERVER -> startService(
                Intent(this, AirPlayService::class.java).setAction(AirPlayService.ACTION_STOP_SERVER),
            )
        }
        finish()
        overridePendingTransition(0, 0)
    }
}
