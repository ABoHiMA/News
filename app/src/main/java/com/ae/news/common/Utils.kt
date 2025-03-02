package com.ae.news.common

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import androidx.core.os.LocaleListCompat
import com.ae.news.R

object Utils {
    var sharedPreferences: SharedPreferences? = null
    const val SAVED_MODE_POS = "SAVED_MODE_POS"
    const val SAVED_LANG_POS = "SAVED_LANG_POS"
    const val URL = "URL"
    const val GOOGLE = "https://www.google.com"

    fun alertDialog(
        context: Context, message: String, onAccept: () -> Unit, onDecline: () -> Unit
    ): AlertDialog {
        val dialog = AlertDialog.Builder(context).setMessage(message)
            .setTitle(getString(context, R.string.alert)).setCancelable(false)
            .setPositiveButton(R.string.ok) { _, _ ->
                onAccept.invoke()
            }.setNegativeButton(R.string.cancel) { dialog, _ ->
                onDecline.invoke()
                dialog.cancel()
            }.create()

        dialog.setOnShowListener {
            dialog.window?.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        context, R.color.primary_color
                    )
                )
            )
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                ?.setTextColor(ContextCompat.getColor(context, R.color.on_primary_color))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                ?.setTextColor(ContextCompat.getColor(context, R.color.on_primary_color))
        }

        return dialog
    }

    fun setLanguage(selectedLanguage: Int) {
        val appLocales = when (selectedLanguage) {
            0 -> LocaleListCompat.forLanguageTags("en")
            1 -> LocaleListCompat.forLanguageTags("ar")
            else -> LocaleListCompat.forLanguageTags("en")
        }
        AppCompatDelegate.setApplicationLocales(appLocales)
    }

    fun getDeviceTheme(context: Context): Int {
        // hint: 1 = Light , 2 = Dark
        try {
            val themeMode = Settings.Secure.getInt(context.contentResolver, "ui_night_mode")
            return themeMode
        } catch (e: Settings.SettingNotFoundException) {
            return -1
        }
    }

    fun setMode(selectedTheme: Int) {
        val appTheme = when (selectedTheme) {
            0 -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            1 -> AppCompatDelegate.MODE_NIGHT_NO
            2 -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(appTheme)
    }

    fun restoreAppTheme() {
        val currentTheme = sharedPreferences?.getInt(SAVED_MODE_POS, 0) ?: 0
        setMode(currentTheme)
    }
}