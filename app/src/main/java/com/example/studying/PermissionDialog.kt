package com.example.studying

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class PermissionDialog :DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.setTitle("Разрешение доступа к контактам")
                    .setMessage("Ну очень нужно")
                    .setCancelable(true)
                    .setPositiveButton("Allow") { _, _ ->
                        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), MY_PERMISSION_REQUEST_CONTACTS)
                    }
                    .setNegativeButton("Deny"
                    ) { _, _ ->
                        Toast.makeText(activity, "Как хочешь", Toast.LENGTH_LONG).show()
                    }
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }
