package org.cf0x.konamiku.nfc

import android.app.Activity
import android.nfc.NfcAdapter
import android.os.Build
import android.util.Log

/** Controls the foreground Activity's NFC poll/listen technologies. */
object NfcDiscoveryController {

    private const val TAG = "KonamikU-NfcDiscovery"

    /**
     * While the app is in the foreground and emulation is active, keep only
     * NFC-F polling and NFC-F listening enabled. This API was added in API 35.
     */
    fun enableFelicaOnly(activity: Activity, adapter: NfcAdapter?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) return

        runCatching {
            adapter?.setDiscoveryTechnology(
                activity,
                NfcAdapter.FLAG_READER_NFC_F,
                NfcAdapter.FLAG_LISTEN_NFC_PASSIVE_F,
            )
        }.onFailure {
            Log.w(TAG, "Failed to enable NFC-F-only discovery", it)
        }
    }

    /** Restore the normal device discovery configuration for this Activity. */
    fun reset(activity: Activity, adapter: NfcAdapter?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) return

        runCatching {
            adapter?.resetDiscoveryTechnology(activity)
        }.onFailure {
            Log.w(TAG, "Failed to restore NFC discovery technologies", it)
        }
    }
}
