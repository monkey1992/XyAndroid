package com.xy.android.sdk.bluetooth

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import com.xy.android.sdk.BackActivity
import org.jetbrains.anko.toast

class BluetoothActivity : BackActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isBluetoothPermissionGranted =
            packageManager.checkPermission(
                Manifest.permission.BLUETOOTH,
                packageName
            ) == PackageManager.PERMISSION_GRANTED
        val isBluetoothConnectPermissionGranted =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                packageManager.checkPermission(
                    Manifest.permission.BLUETOOTH_CONNECT,
                    packageName
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        var isEnabled = false
        if (isBluetoothPermissionGranted) {
            val bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
            val bluetoothAdapter = bluetoothManager.adapter
            isEnabled = bluetoothAdapter?.isEnabled == true
        }
        toast("isEnabled: $isEnabled")
    }
}