package com.f0x1d.logfox.arch.presentation.ui

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Window
import androidx.annotation.AttrRes
import androidx.core.view.WindowCompat
import com.f0x1d.logfox.arch.R
import com.f0x1d.logfox.arch.contrastedNavBarAvailable
import com.f0x1d.logfox.arch.gesturesAvailable

fun Window.enableEdgeToEdge(isContrastEnforced: Boolean = true) {
    WindowCompat.setDecorFitsSystemWindows(this, false)

    val insetsController = WindowCompat.getInsetsController(this, decorView)
    
    val isLightTheme = context.resolveBoolean(
        attributeResId = androidx.appcompat.R.attr.isLightTheme,
        defaultValue = false,
    )

    insetsController.apply {
        isAppearanceLightStatusBars = isLightTheme
        isAppearanceLightNavigationBars = isLightTheme
    }

    // Set navigation bar color based on device capabilities
    val navBarColor = when {
        !contrastedNavBarAvailable -> context.getColor(
            R.color.transparent_black
        )

        !gesturesAvailable && isContrastEnforced -> context.getColor(
            R.color.navbar_transparent_background
        )

        else -> Color.TRANSPARENT
    }
    
    // These APIs are deprecated, but they're still the most reliable way to set system bar colors
    // across all Android versions that this app supports
    @Suppress("DEPRECATION")
    this.statusBarColor = Color.TRANSPARENT
    
    @Suppress("DEPRECATION")
    this.navigationBarColor = navBarColor
}

private fun Context.resolveAttribute(@AttrRes attributeResId: Int) = TypedValue().let {
    when (theme.resolveAttribute(attributeResId, it, true)) {
        true -> it

        else -> null
    }
}

private fun Context.resolveBoolean(@AttrRes attributeResId: Int, defaultValue: Boolean = false) = resolveAttribute(
    attributeResId = attributeResId,
).let {
    when (it != null && it.type == TypedValue.TYPE_INT_BOOLEAN) {
        true -> it.data != 0

        else -> defaultValue
    }
}
